/*
 * Copyright (C) 2005-2007 Alfresco Software Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

 * As a special exception to the terms and conditions of version 2.0 of 
 * the GPL, you may redistribute this Program in connection with Free/Libre 
 * and Open Source Software ("FLOSS") applications as described in Alfresco's 
 * FLOSS exception.  You should have recieved a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 * http://www.alfresco.com/legal/licensing"
 */
package org.alfresco.repo.content;

import java.nio.channels.ReadableByteChannel;

import org.alfresco.service.cmr.repository.ContentIOException;
import org.alfresco.service.cmr.repository.ContentReader;

/**
 * A blank reader for which <code>exists()</code> always returns false.
 * 
 * @author Derek Hulley
 */
public class EmptyContentReader extends AbstractContentReader
{
    /**
     * @param contentUrl    the content URL
     */
    public EmptyContentReader(String contentUrl)
    {
        super(contentUrl);
    }
    
    /**
     * @return  Returns an instance of the this class
     */
    @Override
    protected ContentReader createReader() throws ContentIOException
    {
        return new EmptyContentReader(this.getContentUrl());
    }

    @Override
    protected ReadableByteChannel getDirectReadableChannel() throws ContentIOException
    {
        throw new UnsupportedOperationException("The content never exists");
    }

    public boolean exists()
    {
        return false;
    }

    public long getLastModified()
    {
        return 0L;
    }

    public long getSize()
    {
        return 0L;
    }
}
