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

/**
 *
 * @author olaurino
 */
public interface ISegmentParameter {
    public String getName();

    public int[] getShape();

    public String getUCD();

    public String getUnitString();

    public String getUtype();

    public String getDescription();

    public boolean isArray();

    public int getElementSize();

    public Class getContentClass();

    public Object getValue();

    public String getValueAsString(int maxLength);

    public void setValue(Object value);

    public void setValueFromString(String string);
}
