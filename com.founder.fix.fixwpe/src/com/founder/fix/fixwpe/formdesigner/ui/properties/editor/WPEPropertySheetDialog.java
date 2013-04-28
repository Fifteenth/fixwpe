package com.founder.fix.fixwpe.formdesigner.ui.properties.editor;

import java.util.HashMap;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.json.JSONObject;

import com.founder.fix.studio.FixImageProvider;
import com.founder.fix.studio.formdesigner.common.FormConst.ElementInfo;
import com.founder.fix.studio.formdesigner.ui.properties.sheetpage.FormPropertySheet;
import com.founder.fix.studio.platformdesigner.utils.FixUtil;

/**
 * @author BB
 * 
 */
public class WPEPropertySheetDialog extends TitleAreaDialog {

	HashMap<String, Object> _nodeMap = null;
	JSONObject _nodeJson = null;
	
	String InitValue = "";
	String id = null;
	String name = null;
	String value = "";
	String jsonConfigStr = "";
	ElementInfo elementInfo = null;
	WPEComponentDialogProperty componentDialogProperty = null;
	Text text;

	/**
	 * @param parentShell
	 */
	public WPEPropertySheetDialog(Shell parentShell, ElementInfo elementInfo,
			String initValue, 
			HashMap<String, Object> nodeMap,JSONObject nodeJson
//			,HashMap<String, Object> translateMap
			) {
		super(parentShell);
		setHelpAvailable(false);
		this.InitValue = initValue;
		
		this.elementInfo = elementInfo;
		this._nodeMap = nodeMap;
		this._nodeJson = nodeJson;
		// 取主视图，并实现驱动器
		this.id = this._nodeMap.get("name").toString();
		this.name = this._nodeMap.get("caption").toString();
		Object json = this._nodeMap.get("jsonconfig");
		this.jsonConfigStr = json == null ? "" : json.toString();
		if (this.InitValue == null || this.InitValue.equals(""))
			this.InitValue = this.jsonConfigStr;
		// this.setMessage("属性编辑器");
		
		setTitleImage(FixUtil.getImageFromURL(FixImageProvider.CREATE_FILE_DIALOG));
		
		
	}

	@Override
	protected Point getInitialSize() {
		// TODO Auto-generated method stub
		return new Point(350, 450);
		// return super.getInitialSize();
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		this.setTitle("配置属性");
		this.setMessage(this.name);

		Composite container = (Composite) super.createDialogArea(parent);
		this.createPropertyControls(container);

		return super.createDialogArea(parent);
	}

	private void createPropertyControls(Composite container) {
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite parent = new Composite(container, SWT.BORDER);
		GridLayout gl = new GridLayout();
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		parent.setLayout(gl);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 280;

		parent.setLayoutData(gd);

		// 需要显示的属性框
		FormPropertySheet formPropertySheet = new FormPropertySheet();
		formPropertySheet.createControl(parent);
		formPropertySheet.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));

		IPropertySourceProvider propertySourceProvider = new IPropertySourceProvider() {

			public IPropertySource getPropertySource(Object object) {
				if (object != null && object.toString().equals("_init_")) {
					WPEComponentDialogProperty _ComponentDialogProperty = 
							new WPEComponentDialogProperty(
							elementInfo, InitValue, 
							_nodeMap, _nodeJson,
							WPEPropertySheetDialog.this);
					componentDialogProperty = _ComponentDialogProperty;
					return _ComponentDialogProperty;
				} else
					return null;// new HtmlNodeProperty(mainEditor);

			}
		};

		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(308, 20));
		label.setText("配置项说明");
		FontData newFontData = label.getFont().getFontData()[0];
	    newFontData.setStyle(SWT.BOLD);
	    Font newFont = new Font(this.getShell().getDisplay(), newFontData);
	    label.setFont(newFont);
		
		text = new Text(parent, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
		text.setLayoutData(new GridData(308, 50));
		text.setEditable(false);
		
		// 显示属性
		formPropertySheet.setPropertySourceProvider(propertySourceProvider);
		formPropertySheet.selectionChanged(null, new StructuredSelection("_init_"));
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			this.value = null;
			if (componentDialogProperty != null) {
				this.value = componentDialogProperty.getValue();
				//进行为空判断
				if(value != null && value.startsWith("isNull.")) {
					setErrorMessage(value.split("\\.")[1] + "不能为空");
					return;
				} else {
					setErrorMessage(null);
				}
				componentDialogProperty.setIsNullList(null);
			}
		} else {
			this.value = null;
		}
		super.buttonPressed(buttonId);
	}

	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		
		//Button button1 = this.getButton(OK);
		//Button button = this.createButton(button1.getParent(), 3, "", true);
		//button1.getShell().setDefaultButton(button);
		
		return control;
	}

	@Override
	protected void okPressed() {
		super.okPressed();
	}

	public String getValue() {
		return this.value;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

}
