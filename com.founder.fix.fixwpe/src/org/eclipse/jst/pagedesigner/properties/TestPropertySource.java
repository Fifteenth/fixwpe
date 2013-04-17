package org.eclipse.jst.pagedesigner.properties;


import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class TestPropertySource implements IPropertySource {

	private static final String TEST1_ID = "Test1"; //$NON-NLS-1$

	private static final String TEST2_ID = "Test2"; //$NON-NLS-1$

	public Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descriptors = new PropertyDescriptor[2];
		descriptors[0] = new TextPropertyDescriptor(TEST1_ID, "test1"); //$NON-NLS-1$
		descriptors[1] = new ComboBoxPropertyDescriptor(TEST2_ID, "test2", //$NON-NLS-1$
				new String[] { "true", "false" });  //$NON-NLS-1$//$NON-NLS-2$
		return descriptors;
	}

	public Object getPropertyValue(Object id) {
		if (id.equals(TEST1_ID)) {
			return "test sub"; //$NON-NLS-1$
		} else if (id.equals(TEST2_ID)) {
			return 0;
		}
		return null;
	}

	public boolean isPropertySet(Object id) {
		return false;
	}

	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub
		
	}

	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub
		
	}

}
