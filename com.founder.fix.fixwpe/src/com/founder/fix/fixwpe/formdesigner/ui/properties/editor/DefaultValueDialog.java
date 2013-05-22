/**
 * 
 */
package com.founder.fix.fixwpe.formdesigner.ui.properties.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.founder.fix.apputil.util.XmlUtil;
import com.founder.fix.base.wpe.FixImageProvider;
import com.founder.fix.ocx.platformdesigner.trees.EntityElement;
import com.founder.fix.ocx.platformdesigner.trees.ITreeElement;
import com.founder.fix.ocx.platformdesigner.trees.TreeViewerContentProvider;
import com.founder.fix.ocx.util.FixUtil;

/**
 * @author BB
 *
 */
public class DefaultValueDialog extends TitleAreaDialog {

	HashMap<String,Object> Map = null;
	Text ConfigText = null;
	String InitValue = "";
	String id = null;
	String name = null;
	String value = "";
	String jsonConfigStr = "";
		
	private TreeViewer treeViewer ;
	private ITreeElement selectElement;
	private List<ITreeElement> treeElements;
	/**
	 * @param parentShell
	 */
	public DefaultValueDialog(Shell parentShell, String initValue, HashMap<String,Object> map) 
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
	
	public DefaultValueDialog(Shell parentShell, String initValue) 
	{
		
		super(parentShell);
		setHelpAvailable(false);
		this.InitValue = initValue;
//		this.id = this.Map.get("name").toString();
//	    this.name = this.Map.get("caption").toString();
//	    Object json = this.Map.get("jsonconfig");
//	    this.jsonConfigStr = json==null?"":json.toString();
//	    if ( this.InitValue == null || this.InitValue.equals("") )
//	    	this.InitValue = this.jsonConfigStr;
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
		ConfigText.setLayoutData(new GridData(400,190));
		ConfigText.setText(this.InitValue);
		
		ConfigText.setSelection(InitValue.length(), InitValue.length());
		ConfigText.setFocus();
		
		Font font = new Font(null, "Courier New", 9, 0);
		ConfigText.setFont(font);
		
		
		
		treeElements = new ArrayList<ITreeElement>();
		initTreeData();
		treeViewer = new TreeViewer(firstClient, SWT.NONE);
		Tree tree = treeViewer.getTree();
		GridData gd_tree = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_tree.heightHint = 176;
		gd_tree.widthHint = 260;
		tree.setLayoutData(gd_tree);
		
		//设置内容提供器
		treeViewer.setContentProvider(new TreeViewerContentProvider());
		
		//启用tooltip
		ColumnViewerToolTipSupport.enableFor(treeViewer);
		CellLabelProvider labelProvider = new CellLabelProvider() {
            /* (non-Javadoc)
             * @see org.eclipse.jface.viewers.ViewerLabelProvider#getTooltipText(java.lang.Object)
             */
			@Override
            public String getToolTipText(Object element) {
				if(((EntityElement)element).getTooltip()!=null){
					return ((EntityElement)element).getTooltip() + "";
				}
				else{
					return null;
				}
            }
			
            /* (non-Javadoc)
             * @see org.eclipse.jface.viewers.ViewerLabelProvider#getTooltipShift(java.lang.Object)
             */
            @Override
            public Point getToolTipShift(Object object) {
                    return new Point(5,5);
            }
            
            /* (non-Javadoc)
             * @see org.eclipse.jface.viewers.ViewerLabelProvider#getTooltipDisplayDelayTime(java.lang.Object)
             */
            
            @Override
            public int getToolTipDisplayDelayTime(Object object) {
                    return 1;

            }

            /* (non-Javadoc)
             * @see org.eclipse.jface.viewers.ViewerLabelProvider#getTooltipTimeDisplayed(java.lang.Object)
             */
            @Override
            public int getToolTipTimeDisplayed(Object object) {

                    return 5000;
            }
            
            @Override
            public void update(ViewerCell cell) {
            	    cell.setText(((EntityElement)cell.getElement()).getName());
            	    cell.setImage(getImage((EntityElement)cell.getElement()));
            }
            
            @Override
            public Image getToolTipImage(Object object) {
        		return null;
        	}
			            
		};
			 

		//设置标签提供器
//		treeViewer.setLabelProvider(new TreeViewerLabelProvider());
		treeViewer.setLabelProvider(labelProvider);
		
		//设置内容
		treeViewer.setInput(treeElements);
		
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			
			public void doubleClick(DoubleClickEvent event) {
				selectElement = FixUtil.getSelectElement(treeViewer);
				if(selectElement.getId() != null) {
//					buttonPressed(Dialog.OK);
					
					//光标位置
					int cursorPosition = ConfigText.getCaretPosition();
					String string = ConfigText.getText();
					
					ConfigText.setText(string.substring(0, cursorPosition)
							+selectElement.getId()
							+string.substring(cursorPosition, string.length()));
					
					int newCursorPosition = cursorPosition + selectElement.getId().length();
					ConfigText.setSelection(newCursorPosition, newCursorPosition);
					ConfigText.setFocus();
				}
			}
		});
		
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			public void selectionChanged(SelectionChangedEvent event) {
				selectElement = FixUtil.getSelectElement(treeViewer);
				if(selectElement.getId() != null) {
//					getButton(IDialogConstants.OK_ID).setEnabled(true);
				} else {
//					getButton(IDialogConstants.OK_ID).setEnabled(false);
				}
			}
		});
		
	}
	
	@Override
	protected void buttonPressed(int buttonId) 
	{
		if (buttonId == IDialogConstants.OK_ID) 
		{
            this.value = this.ConfigText.getText();
            if(Map!=null){
            	 Object jsontype = this.Map.get("jsontype");
         		String jsonType = jsontype==null?"string":jsontype.toString();
         		if ( jsonType.equals("object")&&!this.value.startsWith("{"))
         			this.value = "{"+this.value+"}";
         		else if ( jsonType.equals("array")&&!this.value.startsWith("["))
         			this.value = "["+this.value+"]";
                 //this.value = this.value.trim();
                 //this.value = this.value.replace("\\r", "").replace("\\n", "");
            }
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
	
	
	/**
	 * 加载xml数据
	 */
	public void initTreeData() {
		IFolder xmlFolder = FixUtil.getWebProject().getFolder("webconf/designer");
		try {
			for (int i = 0; i < xmlFolder.members().length; i++) {
				IResource iResource = xmlFolder.members()[i];
				if(iResource instanceof IFile && iResource.getName().startsWith("formeditor_")) {
					
					Document document = XmlUtil.read(iResource.getLocation().toFile(), "UTF-8");
					Element root = document.getRootElement();
					
					List<Element> types = root.elements("type");
					if(types != null && types.size() > 0) {
						for (Iterator iterator = types.iterator(); iterator.hasNext();) {
							Element type = (Element) iterator.next();
							
							ITreeElement parentElement = new EntityElement(null,
									type.attributeValue("id"), EntityElement.PROJECT_ECLIPSE);
							
							List<Element> values = type.elements("value");
							if(values != null && values.size() > 0) {
								for (Iterator iterator2 = values.iterator(); iterator2
										.hasNext();) {
									Element value = (Element) iterator2.next();
									
									ITreeElement sonElement = new EntityElement(value.attributeValue("id"),
											value.getText(), EntityElement.BPMN,value.attributeValue("tooltip"));
									
									parentElement.addChild(sonElement);
								}
							}
							
							this.treeElements.add(parentElement);
						}
					}
				}
			}
		} catch (Exception e) {
			MessageDialog.openError(getShell(), "", e.toString());
			e.printStackTrace();
		}
	}
	
	
	
	public Image getImage(ITreeElement tElement){
		// TODO Auto-generated method stub
				// 系统自定义图标
				String img1 = ISharedImages.IMG_TOOL_PASTE;
				String img2 = ISharedImages.IMG_OBJ_FOLDER;
				String img3 = ISharedImages.IMG_OBJ_FILE;
				String img4 = ISharedImages.IMG_ETOOL_DEF_PERSPECTIVE;
				String img5 = ISharedImages.IMG_OBJ_PROJECT;
				
				int icon = tElement.getIcon();
				if(icon == EntityElement.PROJECT) {
					return PlatformUI.getWorkbench().getSharedImages().getImage(img1);
				} else if(icon == EntityElement.FOLDER) {
					return PlatformUI.getWorkbench().getSharedImages().getImage(img2);
				} else if(icon == EntityElement.FILE) {
					return PlatformUI.getWorkbench().getSharedImages().getImage(img3);
				} else if(icon == EntityElement.VIEW) {
					return PlatformUI.getWorkbench().getSharedImages().getImage(img4);
				} else if(icon == EntityElement.PROJECT_ECLIPSE) {
					return PlatformUI.getWorkbench().getSharedImages().getImage(img5);
				} else if(icon == EntityElement.DATASOURCE) {
					return FixUtil.getImageFromURL(FixImageProvider.DATASOURCE);
				} else if(icon == EntityElement.BPMN) {
					return FixUtil.getImageFromURL(FixImageProvider.BPMN_ICON);
				} else if(icon == EntityElement.JAVA) {
					return FixUtil.getImageFromURL(FixImageProvider.JAVA);
				} else if(icon == EntityElement.HTML) {
					return FixUtil.getImageFromURL(FixImageProvider.HTML);
				} else if(icon == EntityElement.XML) {
					return FixUtil.getImageFromURL(FixImageProvider.XML);
				} else if(icon == EntityElement.VIW) {
					return FixUtil.getImageFromURL(FixImageProvider.VIW);
				} else if(icon == EntityElement.SEV) {
					return FixUtil.getImageFromURL(FixImageProvider.SEV);
				} else if(icon == EntityElement.BIZ) {
					return FixUtil.getImageFromURL(FixImageProvider.BIZ);
				} else if(icon == EntityElement.SEV_METHOD) {
					return FixUtil.getImageFromURL(FixImageProvider.SEV_METHOD);
				} else if(icon == EntityElement.LINK_SOURCE) {
					return FixUtil.getImageFromURL(FixImageProvider.LINK_SOURCE);
				} else if(icon == EntityElement.MODULE) {
					return FixUtil.getImageFromURL(FixImageProvider.MODULE);
				} else if(icon == EntityElement.SEV_PACKAGE) {
					return FixUtil.getImageFromURL(FixImageProvider.SEV_PACKAGE);
				} else if(icon == EntityElement.BIZ_PACKAGE) {
					return FixUtil.getImageFromURL(FixImageProvider.BIZ_PACKAGE);
				} else if(icon == EntityElement.BPMN_PACKAGE) {
					return FixUtil.getImageFromURL(FixImageProvider.BPMN_PACKAGE);
				} else if(icon == EntityElement.CONFIG_PACKAGE) {
					return FixUtil.getImageFromURL(FixImageProvider.CONFIG_PACKAGE);
				} else if(icon == EntityElement.FORM_VIEW) {
					return FixUtil.getImageFromURL(FixImageProvider.FORM_VIEW);
				} else if(icon == EntityElement.OTHER) {
					return FixUtil.getImageFromURL(FixImageProvider.OTHER);
				} else if(icon == EntityElement.SPRING) {
					return FixUtil.getImageFromURL(FixImageProvider.SPRING);
				} else if(icon == EntityElement.PROJECT_PACKAGE) {
					return FixUtil.getImageFromURL(FixImageProvider.PROJECT_PACKAGE);
				} else if(icon == EntityElement.JSP) {
					return FixUtil.getImageFromURL(FixImageProvider.JSP);
				} else if(icon == EntityElement.JS) {
					return FixUtil.getImageFromURL(FixImageProvider.JS);
				} else if(icon == EntityElement.IMG) {
					return FixUtil.getImageFromURL(FixImageProvider.IMG);
				} else if(icon == EntityElement.CSS) {
					return FixUtil.getImageFromURL(FixImageProvider.CSS);
				} else if(icon == EntityElement.JAVA_PROJECT) {
					return FixUtil.getImageFromURL(FixImageProvider.JAVA_PROJECT);
				} else if(icon == EntityElement.WEB_PROJECT) {
					return FixUtil.getImageFromURL(FixImageProvider.WEB_PROJECT);
				} else if(icon == EntityElement.MENU) {
					return FixUtil.getImageFromURL(FixImageProvider.MENU);
				} else if(icon == EntityElement.OBJFORM) {
					return FixUtil.getImageFromURL(FixImageProvider.OBJFORM);
				} else if(icon == EntityElement.DICTIONARYTABLE) {
					return FixUtil.getImageFromURL(FixImageProvider.DICTIONARYTABLE);
				} else if(icon == EntityElement.DATASOURCE_LEAF) {
					return FixUtil.getImageFromURL(FixImageProvider.DATASOURCE_LEAF);
				} else if(icon == EntityElement.EVENT) {
					return FixUtil.getImageFromURL(FixImageProvider.EVENT);
				} else if(icon == EntityElement.UNIFORM) {
					return FixUtil.getImageFromURL(FixImageProvider.UNIFORM);
				} else if(icon == EntityElement.FORM) {
					return FixUtil.getImageFromURL(FixImageProvider.FORM);
				} else if(icon == EntityElement.COMPONENTS) {
					return FixUtil.getImageFromURL(FixImageProvider.COMPONENTS);
				} else if(icon == EntityElement.BIZ_COMPONENTS) {
					return FixUtil.getImageFromURL(FixImageProvider.BIZ_COMPONENTS);
				} else if(icon == EntityElement.FUNC_COMPONENTS) {
					return FixUtil.getImageFromURL(FixImageProvider.FUNC_COMPONENTS);
				} else if(icon == EntityElement.SEV_COMPONENTS) {
					return FixUtil.getImageFromURL(FixImageProvider.SEV_COMPONENTS);
				} else if(icon == EntityElement.FORM_COMPONENTS) {
					return FixUtil.getImageFromURL(FixImageProvider.FORM_COMPONENTS);
				} else if(icon == EntityElement.JS_COMPONENT) {
					return FixUtil.getImageFromURL(FixImageProvider.JS_COMPONENT);
				} else if(icon == EntityElement.SERVICE_COMPONENT) {
					return FixUtil.getImageFromURL(FixImageProvider.SERVICE_COMPONENT);
				}
				return null;
	}

}
