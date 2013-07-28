/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.nodetype;

import java.util.List;

/**
 * The <code>NodeTypeTemplate</code> interface is used to define node types which
 * are then registered through the <code>NodeTypeManager.registerNodeType</code> method.
 * <p>
 * <code>NodeTypeTemplate</code>, like <code>NodeType</code>, is a subclass of
 * <code>NodeTypeDefinition</code> so it shares with <code>NodeType</code> those
 * methods that are relevant to a static definition. In addition,
 * <code>NodeTypeTemplate</code> provides methods for setting the attributes of
 * the definition.
 * <p>
 * See the corresponding <code>get</code> methods for each attribute in
 * <code>NodeTypeDefinition</code> for the default values assumed when a new
 * empty <code>NodeTypeTemplate</code> is created (as opposed to one extracted
 * from an existing <code>NodeType</code>).
 *
 * @since JCR 2.0
 */
public interface NodeTypeTemplate extends NodeTypeDefinition {

    /**
     * Sets the name of the node type. This must be a JCR name in either
     * qualified or expanded form.
     *
     * @param name a JCR name.
     * @throws ConstraintViolationException if <code>name</code> is not a
     * syntactically valid JCR name in either qualified or expanded form.
     */
    public void setName(String name) throws ConstraintViolationException;

    /**
     * Sets the names of the supertypes of the node type.
     * These must be a JCR names in either
     * qualified or expanded form.
     *
     * @param names an array of JCR names.
     * @throws ConstraintViolationException if <code>names</code> includes a
     * name that is not a syntactically valid JCR name in either qualified or expanded form.
     */
    public void setDeclaredSuperTypeNames(String[] names) throws ConstraintViolationException;

    /**
     * Sets the abstract flag of the node type.
     *
     * @param abstractStatus a <code>boolean</code>.
     */
    public void setAbstract(boolean abstractStatus);

    /**
     * Sets the mixin flag of the node type.
     *
     * @param mixin a <code>boolean</code>.
     */
    public void setMixin(boolean mixin);

    /**
     * Sets the orderable child nodes flag of the node type.
     *
     * @param orderable a <code>boolean</code>.
     */
    public void setOrderableChildNodes(boolean orderable);

    /**
     * Sets the name of the primary item. This must be a JCR name in either
     * qualified or expanded form.
     *
     * @param name a JCR name.
     * @throws ConstraintViolationException if <code>name</code> is not a
     * syntactically valid JCR name in either qualified or expanded form.
     */
    public void setPrimaryItemName(String name) throws ConstraintViolationException;

    /**
     * Sets the queryable status of the node type.
     *
     * @param queryable a <code>boolean</code>.
     */
    public void setQueryable(boolean queryable);


    /**
     * Returns a mutable <code>List</code> of <code>PropertyDefinitionTemplate</code>
     * objects. To define a new <code>NodeTypeTemplate</code> or change an
     * existing one, <code>PropertyDefinitionTemplate</code> objects can be
     * added to or removed from this <code>List</code>.
     *
     * @return a mutable <code>List</code> of <code>PropertyDefinitionTemplate</code>
     *         objects.
     */
    public List getPropertyDefinitionTemplates();

    /**
     * Returns a mutable <code>List</code> of <code>NodeDefinitionTemplate</code>
     * objects. To define a new <code>NodeTypeTemplate</code> or change an
     * existing one, <code>NodeDefinitionTemplate</code> objects can be added to
     * or removed from this <code>List</code>.
     *
     * @return a mutable <code>List</code> of <code>NodeDefinitionTemplate</code>
     *         objects.
     */
    public List getNodeDefinitionTemplates();
}
