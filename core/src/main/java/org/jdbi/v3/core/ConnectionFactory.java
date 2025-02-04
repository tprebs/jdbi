/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdbi.v3.core;

import java.sql.Connection;
import java.sql.SQLException;

import org.jdbi.v3.core.statement.Cleanable;

/**
 * Supplies {@link Connection} instances to a created {@link Handle} and allows
 * custom close handling.
 */
@FunctionalInterface
public interface ConnectionFactory {

    /**
     * Opens a connection.
     *
     * @return A {@link Connection} object.
     * @throws SQLException if anything goes wrong
     */
    Connection openConnection() throws SQLException;

    /**
     * Closes a connection.
     *
     * @param conn A {@link Connection} object.
     * @throws SQLException if anything goes wrong
     */
    default void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }

    /**
     * Returns a {@link Cleanable} that will close the connection and release all necessary resources.
     *
     * @param conn A {@link Connection} object.
     * @return A {@link Cleanable} instance. Calling the {@link Cleanable#close()} method will close the connection and release all resources.
     */
    default Cleanable getCleanableFor(Connection conn) {
        return () -> this.closeConnection(conn);
    }
}
