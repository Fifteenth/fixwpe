/**
 * 
 */
package com.founder.fix.fixwpe.formdesigner.ui.properties.editor;

import java.util.HashMap;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author BB
 *
 */
public class PropertyDialog extends TitleAreaDialog {

	HashMap<String,Object> Map = null;
	Text ConfigText = null;
	String InitValue = "";
	String id = null;
	String name = null;
	String value = "";
	String jsonConfigStr = "";
		
	/**
	 * @param parentShell
	 */
	public PropertyDialog(Shell parentShell, String initValue, HashMap<String,Object> map) 
	{
		
		super(parentShell);
		setHelpAvailable(false);
		this.InitValue = initValue;
		this.Map = map;
		this.id = this.Map.get("name").toString();
	    this.name = this.Map.get("caption").toString();
	    Object json = this.Map.get("jsonconfig");
	    this.jsonConfigStr = json==null?"":json.toString();
	    if ( this.InitValue == null || this.InitValue.equals("") )
	    	this.InitValue = this.jsonConfigStr;
		//this.setMessage("属性编辑器");
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		
		this.setTitle("配置属性");
		this.setMessage(this.name);
		
		Composite container = (Composite) super.createDialogArea(parent);
		this.createPropertyControls(container);
		
		return super.createDialogArea(parent);
	}
	
	private void createPropertyControls(Composite container)
	{		
		Composite parent=new Composite(container, SWT.NULL);
		parent.setLayout(new GridLayout());
		Composite firstClient=new Composite(parent,SWT.NULL);
		firstClient.setLayout(new GridLayout(3,false));
		
		
		///XMLEditor htmlCodeEditor = new XMLEditor();
	
		
		ConfigText=new Text(firstClient,SWT.MULTI|SWT.BORDER|SWT.WRAP|SWT.V_SCROLL);
		ConfigText.setLayoutData(new GridData(510,100));
		ConfigText.setText(this.InitValue);
		
		Font font = new Font(null, "Courier New", 9, 0);
		ConfigText.setFont(font);
		
		
		
		
	}
	
	@Override
	protected void buttonPressed(int buttonId) 
	{
		if (buttonId == IDialogConstants.OK_ID) 
		{
            this.value = this.ConfigText.getText();
            
            Object jsontype = this.Map.get("jsontype");
    		String jsonType = jsontype==null?"string":jsontype.toString();
    		if ( jsonType.equals("object")&&!this.value.startsWith("{"))
    			this.value = "{"+this.value+"}";
    		else if ( jsonType.equals("array")&&!this.value.startsWith("["))
    			this.value = "["+this.value+"]";
            
            //this.value = this.value.trim();
            //this.value = this.value.replace("\\r", "").replace("\\n", "");
        } 
		else 
        {
        	this.value = null;
        }
		super.buttonPressed(buttonId);
	}
	
	@Override
	protected Control createContents(Composite parent) {
		
		return super.createContents(parent);
	}
	
	@Override
	protected void okPressed() 
	{
		super.okPressed();
	}
	
	public String getValue()
	{
		return this.value;
	}

}
