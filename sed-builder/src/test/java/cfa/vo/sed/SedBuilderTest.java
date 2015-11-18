/**
 * Copyright (C) 2015 Smithsonian Astrophysical Observatory
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
package cfa.vo.sed;

import cfa.vo.iris.IrisComponentInterface;
import cfa.vo.iris.test.unit.AbstractComponentSuite;
import cfa.vo.sed.builder.AsciiConfT;
import cfa.vo.sed.builder.SedBuilder;
import cfa.vo.sed.builder.SegmentImporterT;
import cfa.vo.sed.filters.*;
import org.junit.runners.Suite;

@Suite.SuiteClasses({
        SegmentImporterT.class,
        AsciiConfT.class,
        FileFormatsT.class,
        FITSFilterT.class,
        PhotometryCatalogT.class,
        PhotometryPointCloneT.class,
        SameUrlDifferentFilesT.class,
})
public class SedBuilderTest extends AbstractComponentSuite {
        @Override
        protected Class<? extends IrisComponentInterface> getComponentClass() {
                return SedBuilder.class;
        }
}
