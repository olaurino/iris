/**
 * Copyright (C) 2012 Smithsonian Astrophysical Observatory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cfa.vo.sed.builder;

import javax.swing.table.TableCellRenderer;

/**
 *
 * @author omarlaurino
 */
public interface ISegmentColumn {

    public String getName();

    public Integer getNumber();

    public String getUnitString();

    public String getUCD();

    public String getUtype();

    public TableCellRenderer getCellRenderer();

    public int[] getShape();

    public int getElementSize();

    public Class getContentClass();

    public String getDescription();
}
