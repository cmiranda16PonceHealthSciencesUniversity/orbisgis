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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.2-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.11.07 at 09:51:04 AM CET 
//


package org.gdms.source.directory;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.gdms.source.directory package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ObjectDefinition_QNAME = new QName("", "object-definition");
    private final static QName _DbDefinition_QNAME = new QName("", "db-definition");
    private final static QName _Definition_QNAME = new QName("", "definition");
    private final static QName _SqlDefinition_QNAME = new QName("", "sql-definition");
    private final static QName _FileDefinition_QNAME = new QName("", "file-definition");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.gdms.source.directory
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DbDefinitionType }
     * 
     */
    public DbDefinitionType createDbDefinitionType() {
        return new DbDefinitionType();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link FileProperty }
     * 
     */
    public FileProperty createFileProperty() {
        return new FileProperty();
    }

    /**
     * Create an instance of {@link FileDefinitionType }
     * 
     */
    public FileDefinitionType createFileDefinitionType() {
        return new FileDefinitionType();
    }

    /**
     * Create an instance of {@link SqlDefinitionType }
     * 
     */
    public SqlDefinitionType createSqlDefinitionType() {
        return new SqlDefinitionType();
    }

    /**
     * Create an instance of {@link Sources }
     * 
     */
    public Sources createSources() {
        return new Sources();
    }

    /**
     * Create an instance of {@link ObjectDefinitionType }
     * 
     */
    public ObjectDefinitionType createObjectDefinitionType() {
        return new ObjectDefinitionType();
    }

    /**
     * Create an instance of {@link DefinitionType }
     * 
     */
    public DefinitionType createDefinitionType() {
        return new DefinitionType();
    }

    /**
     * Create an instance of {@link Source }
     * 
     */
    public Source createSource() {
        return new Source();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectDefinitionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "object-definition")
    public JAXBElement<ObjectDefinitionType> createObjectDefinition(ObjectDefinitionType value) {
        return new JAXBElement<ObjectDefinitionType>(_ObjectDefinition_QNAME, ObjectDefinitionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DbDefinitionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "db-definition")
    public JAXBElement<DbDefinitionType> createDbDefinition(DbDefinitionType value) {
        return new JAXBElement<DbDefinitionType>(_DbDefinition_QNAME, DbDefinitionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DefinitionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "definition")
    public JAXBElement<DefinitionType> createDefinition(DefinitionType value) {
        return new JAXBElement<DefinitionType>(_Definition_QNAME, DefinitionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SqlDefinitionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "sql-definition")
    public JAXBElement<SqlDefinitionType> createSqlDefinition(SqlDefinitionType value) {
        return new JAXBElement<SqlDefinitionType>(_SqlDefinition_QNAME, SqlDefinitionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileDefinitionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "file-definition")
    public JAXBElement<FileDefinitionType> createFileDefinition(FileDefinitionType value) {
        return new JAXBElement<FileDefinitionType>(_FileDefinition_QNAME, FileDefinitionType.class, null, value);
    }

}
