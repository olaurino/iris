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

package cfa.vo.sed.filters;

import com.google.common.io.Files;
import java.io.File;
import java.net.URL;

import org.junit.*;

public class SameUrlDifferentFilesT {

    /**
     * Test that the cache is updated when a file is edited.
     */
    @Test
    public void testSameUrlDifferentFiles() throws Exception {

        URL firstURL = getClass().getResource("/test_data/3c273.xml");
        URL secondURL = getClass().getResource("/test_data/mine.vot");

        File firstFile = new File(firstURL.toURI());
        File secondFile = new File(secondURL.toURI());

        IFilter firstInstance = FilterCache.getInstance(NativeFileFormat.VOTABLE.getFilter(null).getClass(), firstURL);

        Thread.sleep(1000); // the lastModified flag needs to change

        IFilter secondInstance = FilterCache.getInstance(NativeFileFormat.VOTABLE.getFilter(null).getClass(), firstURL);

        Assert.assertTrue("file has not changed, instances should be the same", firstInstance == secondInstance);

        String filename = firstFile.getAbsolutePath().concat("something");
        URL url = new URL("file:"+filename);
        File testFile = new File(filename);

        Files.copy(firstFile, testFile);

        IFilter firstTestInstance = FilterCache.getInstance(NativeFileFormat.VOTABLE.getFilter(null).getClass(), url);

        Thread.sleep(1000); // the lastModified flag needs to change.

        Files.copy(secondFile, testFile);

        IFilter secondTestInstance = FilterCache.getInstance(NativeFileFormat.VOTABLE.getFilter(null).getClass(), url);

        Assert.assertFalse("file has changed, instances should be different", firstTestInstance == secondTestInstance);

    }


}