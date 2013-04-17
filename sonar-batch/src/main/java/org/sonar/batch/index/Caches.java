/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2008-2012 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.batch.index;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.persistit.Exchange;
import com.persistit.Persistit;
import com.persistit.exception.PersistitException;
import com.persistit.logging.Slf4jAdapter;
import org.apache.commons.io.FileUtils;
import org.picocontainer.Startable;
import org.slf4j.LoggerFactory;
import org.sonar.api.BatchComponent;

import java.io.File;
import java.io.Serializable;
import java.util.Properties;
import java.util.Set;

/**
 * Factory of caches
 *
 * @since 3.6
 */
public class Caches implements BatchComponent, Startable {

  private final Set<String> cacheNames = Sets.newHashSet();
  private File tempDir;
  private Persistit persistit;

  public <K extends Serializable, V extends Serializable> Cache<K, V> createCache(String cacheName) {
    Preconditions.checkState(!cacheNames.contains(cacheName), "Cache is already created: " + cacheName);

    try {
      Exchange exchange = persistit.getExchange("sonar-scan", cacheName, true);
      Cache<K, V> cache = new Cache<K, V>(cacheName, exchange);
      cacheNames.add(cacheName);
      return cache;
    } catch (Exception e) {
      throw new IllegalStateException("Fail to create cache: " + cacheName, e);
    }
  }

  @Override
  public void start() {
    try {
      tempDir = Files.createTempDir();
      persistit = new Persistit();
      persistit.setPersistitLogger(new Slf4jAdapter(LoggerFactory.getLogger("PERSISTIT")));
      Properties props = new Properties();
      props.setProperty("datapath", tempDir.getAbsolutePath());
      props.setProperty("buffer.count.8192", "10");
      props.setProperty("logfile", "${datapath}/persistit.log");
      props.setProperty("volume.1", "${datapath}/sonar-scan,create,pageSize:8192,initialSize:1M,extensionSize:1M,maximumSize:10G");
      props.setProperty("journalpath", "${datapath}/journal");
      persistit.setProperties(props);
      persistit.initialize();

      // TODO should use persistit#createTemporaryVolume(), but how ?
      // See https://github.com/akiban/persistit/blob/master/doc/Miscellaneous.rst

    } catch (Exception e) {
      throw new IllegalStateException("Fail to start caches", e);
    }
  }

  @Override
  public void stop() {
    if (persistit != null) {
      try {
        persistit.close(false);
        persistit = null;
      } catch (PersistitException e) {
        throw new IllegalStateException("Fail to close caches", e);
      }
    }
    FileUtils.deleteQuietly(tempDir);
    tempDir = null;
    cacheNames.clear();
  }

  File tempDir() {
    return tempDir;
  }

  Persistit persistit() {
    return persistit;
  }
}
