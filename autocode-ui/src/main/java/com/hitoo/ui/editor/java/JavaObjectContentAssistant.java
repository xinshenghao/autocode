package com.hitoo.ui.editor.java;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

public class JavaObjectContentAssistant implements IContentAssistProcessor {

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		IDocument doc = viewer.getDocument();
		//获取提示列表中的内容
		List<CompletionProposal> list = computObject(getObjectName(doc, offset), offset);
		return list.toArray(new CompletionProposal[list.size()]);
	}

	/**
	 * 设置内容提示中的内容
	 * @param objectName
	 * @param offset
	 * @return
	 */
	private List<CompletionProposal> computObject(String objectName, int offset) {
		//objName 首先看objName是不是 内置对象
		List<CompletionProposal> list = new ArrayList<>();
		boolean bFind =  false;
		for(int i=0; i<JavaCodeKey.key.length; i++) {
			String tempString = JavaCodeKey.key[i];
			if(objectName.equals(tempString)) {
				bFind = true;
				break;
			}
		}
		//如果是内置对象，将所有对象的字符串添加到内容提示列表中
		if(bFind) {
			for(int i=0; i< JavaCodeKey.key.length; i++) {
				String insert =  objectName+"."+ JavaCodeKey.key[i];
				CompletionProposal proposal = new CompletionProposal(insert, offset-objectName.length()-1, 
						objectName.length()+1, insert.length());
				list.add(proposal);
			}
		}
		return list;
	}

	/**
	 * 获取提示之前输入的字符
	 * @param doc
	 * @param offset
	 * @return
	 */
	private String getObjectName(IDocument doc, int offset) {
		//offset是当前光标所在的位置，也就是偏移量
		StringBuffer buf = new StringBuffer();
		offset--;
		//依次从当前位置查找，知道字符串Wie空格或”.“时停止，然后将字符串反转
		while(true) {
			try {
				char c = doc.getChar(--offset);
				if(Character.isWhitespace(c)) {
					break;
				}
				if(c == '.') break;
				buf.append(c);
			} catch (BadLocationException e) {
				e.printStackTrace();
				break;
			}
		}
		return buf.reverse().toString();
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] {'.'};
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
