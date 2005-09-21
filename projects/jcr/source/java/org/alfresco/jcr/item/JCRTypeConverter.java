/*
 * Copyright (C) 2005 Alfresco, Inc.
 *
 * Licensed under the Mozilla Public License version 1.1 
 * with a permitted attribution clause. You may obtain a
 * copy of the License at
 *
 *   http://www.alfresco.org/legal/license.txt
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 */
package org.alfresco.jcr.item;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

import org.alfresco.jcr.session.SessionImpl;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.Path;
import org.alfresco.service.cmr.repository.datatype.DefaultTypeConverter;
import org.alfresco.service.cmr.repository.datatype.TypeConverter;
import org.alfresco.service.namespace.NamespaceException;
import org.alfresco.service.namespace.QName;


/**
 * Responsible for converting Alfresco values to JCR values.
 * 
 * @author David Caruana
 *
 */
public class JCRTypeConverter
{
    private SessionTypeConverter jcrTypeConverter;
    
    /**
     * Construct 
     * 
     * @param session
     */
    public JCRTypeConverter(SessionImpl session)
    {
        this.jcrTypeConverter = new SessionTypeConverter(session);
    }
    
    /**
     * Get the underlying Converter
     * 
     * @return  type converter
     */
    public TypeConverter getConverter()
    {
        return jcrTypeConverter;
    }
    
    
    /**
     * Convert to JCR Reference Value
     * 
     * @param session
     * @param value
     * @return
     * @throws ValueFormatException
     * @throws RepositoryException
     */
    public NodeImpl referenceValue(Object value) throws ValueFormatException, RepositoryException
    {
        try
        {
            NodeRef nodeRef = (NodeRef)jcrTypeConverter.convert(NodeRef.class, value);
            return new NodeImpl(jcrTypeConverter.getSession(), nodeRef);
        }
        catch(Exception e)
        {
            translateException(e);
            throw new RepositoryException(e);
        }
    }
        
    /**
     * Convert to JCR String Value
     * 
     * @param value
     * @return
     * @throws ValueFormatException
     * @throws RepositoryException
     */
    public String stringValue(Object value) throws ValueFormatException, RepositoryException 
    {
        try
        {
            return (String)jcrTypeConverter.convert(String.class, value);
        }
        catch(Exception e)
        {
            translateException(e);
            throw new RepositoryException(e);
        }
    }

    /**
     * Convert to JCR Stream Value
     * 
     * @param value
     * @return
     * @throws IllegalStateException
     * @throws RepositoryException
     */
    public InputStream streamValue(Object value) throws IllegalStateException, RepositoryException
    {
        try
        {
            return (InputStream)jcrTypeConverter.convert(InputStream.class, value);
        }
        catch(Exception e)
        {
            translateException(e);
            throw new RepositoryException(e);
        }
    }

    /**
     * Convert to JCR Long Value
     * 
     * @param value
     * @return
     * @throws ValueFormatException
     * @throws IllegalStateException
     * @throws RepositoryException
     */
    public long longValue(Object value) throws ValueFormatException, IllegalStateException, RepositoryException
    {
        try
        {
            return jcrTypeConverter.longValue(value);
        }
        catch(Exception e)
        {
            translateException(e);
            throw new RepositoryException(e);
        }
    }

    /**
     * Convert to JCR Double Value
     * 
     * @param value
     * @return
     * @throws ValueFormatException
     * @throws IllegalStateException
     * @throws RepositoryException
     */
    public double doubleValue(Object value) throws ValueFormatException, IllegalStateException, RepositoryException
    {
        try
        {
            return jcrTypeConverter.doubleValue(value);
        }
        catch(Exception e)
        {
            translateException(e);
            throw new RepositoryException(e);
        }
    }

    /**
     * Convert to JCR Date Value
     * 
     * @param value
     * @return
     * @throws ValueFormatException
     * @throws IllegalStateException
     * @throws RepositoryException
     */
    public Calendar dateValue(Object value) throws ValueFormatException, IllegalStateException, RepositoryException
    {
        try
        {
            Date date = (Date)jcrTypeConverter.convert(Date.class, value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }
        catch(Exception e)
        {
            translateException(e);
            throw new RepositoryException(e);
        }
    }

    /**
     * Convert to JCR Boolean Value
     * 
     * @param value
     * @return
     * @throws ValueFormatException
     * @throws IllegalStateException
     * @throws RepositoryException
     */
    public boolean booleanValue(Object value) throws ValueFormatException, IllegalStateException, RepositoryException
    {
        try
        {
            return jcrTypeConverter.booleanValue(value);
        }
        catch(Exception e)
        {
            translateException(e);
            throw new RepositoryException(e);
        }
    }
    
    /**
     * Convert to JCR Name Value
     * 
     * @param value
     * @return
     * @throws ValueFormatException
     * @throws IllegalStateException
     * @throws RepositoryException
     */
    public QName nameValue(Object value) throws ValueFormatException, IllegalStateException, RepositoryException
    {
        try
        {
            return jcrTypeConverter.convert(QName.class, value);
        }
        catch(Exception e)
        {
            translateException(e);
            throw new RepositoryException(e);
        }
    }

    /**
     * Convert to JCR Path Value
     * 
     * @param value
     * @return
     * @throws ValueFormatException
     * @throws IllegalStateException
     * @throws RepositoryException
     */
    public Path pathValue(Object value) throws ValueFormatException, IllegalStateException, RepositoryException
    {
        try
        {
            return jcrTypeConverter.convert(Path.class, value);
        }
        catch(Exception e)
        {
            translateException(e);
            throw new RepositoryException(e);
        }
    }

    /**
     * Catch and translate value conversion errors
     * 
     * @param e  exception to translate
     * @throws ValueFormatException  value formatting exception
     */
    private static void translateException(Exception e) throws ValueFormatException 
    {
        if (e instanceof UnsupportedOperationException ||
            e instanceof NumberFormatException)
        {
            throw new ValueFormatException(e);
        }
    }


    /**
     * Data Type Converter that takes into account JCR session context
     * 
     * @author David Caruana
     */
    private static class SessionTypeConverter extends TypeConverter
    {
        private SessionImpl session;
        
        /**
         * Construct
         * 
         * @param session  session context
         */
        public SessionTypeConverter(SessionImpl session)
        {
            this.session = session;
            
            
            /**
             * Converter to translating string to QName as prefix:localName
             */
            addConverter(String.class, QName.class, new TypeConverter.Converter<String, QName>()
            {
                public QName convert(String source)
                {
                    try
                    {
                        return QName.createQName(source, SessionTypeConverter.this.session.getNamespaceResolver());
                    }
                    catch(NamespaceException e)
                    {
                        throw new UnsupportedOperationException("Cannot convert " + source + " to qualified name", e);
                    }
                }
            });

            /**
             * Converter to translating string to QName as prefix:localName
             */
            addConverter(String.class, Path.class, new TypeConverter.Converter<String, Path>()
            {
                public Path convert(String source)
                {
                    try
                    {
                        return new JCRPath(SessionTypeConverter.this.session.getNamespaceResolver(), source).getPath();
                    }
                    catch(NamespaceException e)
                    {
                        throw new UnsupportedOperationException("Cannot convert " + source + " to qualified name", e);
                    }
                }
            });

            /**
             * Converter for translating QName to string as prefix:localName 
             */
            addConverter(QName.class, String.class, new TypeConverter.Converter<QName, String>()
            {
                public String convert(QName source)
                {
                    try
                    {
                        return source.toPrefixString(SessionTypeConverter.this.session.getNamespaceResolver());
                    }
                    catch(NamespaceException e)
                    {
                        throw new UnsupportedOperationException("Cannot convert " + source + " to qualified name", e);
                    }
                }
            });

            /**
             * Converter for translating Path to string as prefix:localName 
             */
            addConverter(Path.class, String.class, new TypeConverter.Converter<Path, String>()
            {
                public String convert(Path source)
                {
                    try
                    {
                        return source.toPrefixString(SessionTypeConverter.this.session.getNamespaceResolver());
                    }
                    catch(NamespaceException e)
                    {
                        throw new UnsupportedOperationException("Cannot convert " + source + " to qualified name", e);
                    }
                }
            });
        }

        /**
         * Get the session
         * 
         * @return  session
         */
        public SessionImpl getSession()
        {
            return session;
        }
        
        /* (non-Javadoc)
         * @see org.alfresco.service.cmr.repository.datatype.TypeConverter#getConverter(java.lang.Class, java.lang.Class)
         */
        @Override
        public <F, T> Converter getConverter(Class<F> source, Class<T> dest)
        {
            Converter converter = super.getConverter(source, dest);
            if (converter == null)
            {
                converter = DefaultTypeConverter.INSTANCE.getConverter(source, dest);
                if (converter instanceof DynamicTwoStageConverter)
                {
                    DynamicTwoStageConverter dynamic = (DynamicTwoStageConverter)converter;
                    converter = addDynamicTwoStageConverter(dynamic.getFrom(), dynamic.getIntermediate(), dynamic.getTo());
                }
            }
            return converter;
        }
    }
    
}
