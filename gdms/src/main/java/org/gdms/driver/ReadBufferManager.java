/*
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able
 * to manipulate and create vector and raster spatial information. OrbisGIS
 * is distributed under GPL 3 license. It is produced  by the geo-informatic team of
 * the IRSTV Institute <http://www.irstv.cnrs.fr/>, CNRS FR 2488:
 *    Erwan BOCHER, scientific researcher,
 *    Thomas LEDUC, scientific researcher,
 *    Fernando GONZALEZ CORTES, computer engineer.
 *
 * Copyright (C) 2007 Erwan BOCHER, Fernando GONZALEZ CORTES, Thomas LEDUC
 *
 * This file is part of OrbisGIS.
 *
 * OrbisGIS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OrbisGIS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OrbisGIS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult:
 *    <http://orbisgis.cerma.archi.fr/>
 *    <http://sourcesup.cru.fr/projects/orbisgis/>
 *
 * or contact directly:
 *    erwan.bocher _at_ ec-nantes.fr
 *    fergonco _at_ gmail.com
 *    thomas.leduc _at_ cerma.archi.fr
 */
package org.gdms.driver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public final class ReadBufferManager {

        private int bufferSize;
        private ByteBuffer buffer;
        private FileChannel channel;
        private long windowStart;
        private long positionInFile;

        /**
         * Instantiates a ReadBufferManager to read the specified channel
         *
         * @param channel
         * @throws IOException
         */
        public ReadBufferManager(FileChannel channel) throws IOException {
                this(channel, 1024 * 32);
        }

        /**
         * Instantiates a ReadBufferManager to read the specified channel. The
         * specified bufferSize is the size of the channel content cached in memory
         *
         * @param channel
         * @param bufferSize
         * @throws IOException
         */
        public ReadBufferManager(FileChannel channel, int bufferSize)
                throws IOException {
                this.channel = channel;
                buffer = ByteBuffer.allocate(0);
                windowStart = 0;
                this.bufferSize = bufferSize;
                getWindowOffset(0, bufferSize);
        }

        /**
         * Moves the window if necessary to contain the desired byte and returns the
         * position of the byte in the window
         *
         * @param bytePos
         * @throws IOException
         */
        private int getWindowOffset(long bytePos, int length) throws IOException {
                long desiredMin = bytePos;
                long desiredMax = desiredMin + length - 1;
                if ((desiredMin >= windowStart)
                        && (desiredMax < windowStart + buffer.capacity())) {
                        long res = desiredMin - windowStart;
                        if (res < Integer.MAX_VALUE) {
                                return (int) res;
                        } else {
                                throw new IOException("this buffer is quite large...");
                        }
                } else {
                        int bufferCapacity = Math.max(bufferSize, length);
                        long size = channel.size();
                        if (size > Integer.MAX_VALUE) {
                                throw new IOException("Woaw ! You want to have a REALLY LARGE buffer !");
                        }
                        bufferCapacity = Math.min(bufferCapacity, (int) (channel.size()
                                - bytePos));
                        windowStart = bytePos;

                        channel.position(windowStart);
                        if (buffer.capacity() != bufferCapacity) {
                                ByteOrder order = buffer.order();
                                buffer = ByteBuffer.allocate(bufferCapacity);
                                buffer.order(order);
                        } else {
                                buffer.clear();
                        }
                        channel.read(buffer);
                        buffer.flip();
                        return (int) (desiredMin - windowStart);
                }
        }

        /**
         * Gets the byte value at the specified position
         *
         * @param bytePos
         * @return
         * @throws IOException
         */
        public byte getByte(long bytePos) throws IOException {
                int windowOffset = getWindowOffset(bytePos, 1);
                return buffer.get(windowOffset);
        }

        /**
         * Gets the size of the channel
         *
         * @return
         * @throws IOException
         */
        public long getLength() throws IOException {
                return channel.size();
        }

        /**
         * Specifies the byte order. One of the constants in {@link ByteBuffer}
         *
         * @param order
         */
        public void order(ByteOrder order) {
                buffer.order(order);
        }

        /**
         * Gets the int value at the specified position
         *
         * @param bytePos
         * @return
         * @throws IOException
         */
        public int getInt(long bytePos) throws IOException {
                int windowOffset = getWindowOffset(bytePos, 4);
                return buffer.getInt(windowOffset);
        }

        /**
         * Gets the long value at the specified position
         *
         * @param bytePos
         * @return
         * @throws IOException
         */
        public long getLong(long bytePos) throws IOException {
                int windowOffset = getWindowOffset(bytePos, 8);
                return buffer.getLong(windowOffset);
        }

        /**
         * Gets the long value at the current position
         *
         * @param bytePos
         * @return
         * @throws IOException
         */
        public long getLong() throws IOException {
                long ret = getLong(positionInFile);
                positionInFile += 8;
                return ret;
        }

        /**
         * Gets the byte value at the current position
         *
         * @return
         * @throws IOException
         */
        public byte get() throws IOException {
                byte ret = getByte(positionInFile);
                positionInFile += 1;
                return ret;
        }

        /**
         * Gets the int value at the current position
         *
         * @return
         * @throws IOException
         */
        public int getInt() throws IOException {
                int ret = getInt(positionInFile);
                positionInFile += 4;
                return ret;
        }

        /**
         * skips the specified number of bytes from the current position in the
         * channel
         *
         * @param numBytes
         * @throws IOException
         */
        public void skip(int numBytes) throws IOException {
                positionInFile += numBytes;
        }

        /**
         * Gets the byte[] value at the current position
         *
         * @param buffer
         * @return
         * @throws IOException
         */
        public ByteBuffer get(byte[] buffer) throws IOException {
                int windowOffset = getWindowOffset(positionInFile, buffer.length);
                this.buffer.position(windowOffset);
                positionInFile += buffer.length;
                return this.buffer.get(buffer);
        }

        /**
         * Gets the byte[] value at the specified position
         *
         * @param pos
         * @param buffer
         * @return
         * @throws IOException
         */
        public ByteBuffer get(long pos, byte[] buffer) throws IOException {
                int windowOffset = getWindowOffset(pos, buffer.length);
                this.buffer.position(windowOffset);
                return this.buffer.get(buffer);
        }

        /**
         * Moves the current position to the specified one
         *
         * @param position
         */
        public void position(long position) {
                this.positionInFile = position;
        }

        /**
         * Gets the positionf of this buffer in the file it's reading.
         * @return
         */
        public long getPosition() {
                return positionInFile;
        }

        /**
         * Gets the double value at the specified position
         *
         * @return
         * @throws IOException
         */
        public double getDouble() throws IOException {
                double ret = getDouble(positionInFile);
                positionInFile += 8;
                return ret;
        }

        /**
         * Gets the double value at the specified position
         *
         * @param bytePos
         * @return
         * @throws IOException
         */
        public double getDouble(long bytePos) throws IOException {
                int windowOffset = getWindowOffset(bytePos, 8);
                return buffer.getDouble(windowOffset);
        }

        /**
         * If the current position is at the end of the channel
         *
         * @return
         * @throws IOException
         */
        public boolean isEOF() throws IOException {
                return (buffer.remaining() == 0)
                        && (windowStart + buffer.capacity() >= channel.size());
        }

        /**
         * Gets the number of remaining bytes in the current file, starting from the
         * current position
         * @return a number of bytes >=0
         * @throws IOException
         */
        public long remaining() throws IOException {
                return channel.size() - windowStart - buffer.position();
        }
}
