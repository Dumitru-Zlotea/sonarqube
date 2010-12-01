#
# Sonar, entreprise quality control tool.
# Copyright (C) 2009 SonarSource SA
# mailto:contact AT sonarsource DOT com
#
# Sonar is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation; either
# version 3 of the License, or (at your option) any later version.
#
# Sonar is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public
# License along with Sonar; if not, write to the Free Software
# Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
#
module DashboardHelper
  include WidgetPropertiesHelper

  def active_widgets_ids_formatted(column)
    active_widget_ids=[]
    @dashboard.widgets.find(:all, :conditions => {:column_index => column}, :order => 'row_index ASC').each do |widget|
      widget_view=nil
      found_index=-1
      @widgets.each_with_index {|item, index|
        if item.getId()==widget.widget_key
          found_index=index
        end
      }
      if found_index>-1
        active_widget_ids=active_widget_ids << (widget.widget_key+"_"+found_index.to_s())
      end
    end
    return "\'"+active_widget_ids.join("\',\'")+"\'"
  end

  def formatted_value(measure, default='')
    measure ? measure.formatted_value : default
  end

  def measure(metric_key)
    @snapshot.measure(metric_key)
  end

end