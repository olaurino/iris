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

package cfa.vo.sed.filters;

import cfa.vo.sed.builder.ISegmentMetadata;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 *
 * @author olaurino
 */
public interface IFilter {

    public Number[] getData(int segment, int column) throws IOException, FilterException;

    public Object[] getColumnData(int segment, int column) throws IOException, FilterException;

    public List<ISegmentMetadata> getMetadata() throws IOException, FilterException;

    public String getDescription();

    public String getName();

    public void setUrl(URL url);

    public URL getUrl();

    public boolean wasModified();

    public String getAuthor();

    public String getVersion();

}
