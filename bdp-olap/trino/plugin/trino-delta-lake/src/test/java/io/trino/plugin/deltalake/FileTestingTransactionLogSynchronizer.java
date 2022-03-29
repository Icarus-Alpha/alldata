/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.plugin.deltalake;

import io.trino.plugin.deltalake.transactionlog.writer.TransactionLogSynchronizer;
import io.trino.spi.connector.ConnectorSession;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.io.UncheckedIOException;

public class FileTestingTransactionLogSynchronizer
        implements TransactionLogSynchronizer
{
    @Override
    public boolean isUnsafe()
    {
        return true;
    }

    @Override
    public void write(ConnectorSession session, String clusterId, Path newLogEntryPath, byte[] entryContents)
    {
        try {
            FileSystem fileSystem = newLogEntryPath.getFileSystem(new Configuration(false));
            try (FSDataOutputStream outputStream = fileSystem.createFile(newLogEntryPath).build()) {
                outputStream.write(entryContents);
            }
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
