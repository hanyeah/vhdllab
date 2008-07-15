/**
 * 
 */
package hr.fer.zemris.vhdllab.communicaton.methods;

import hr.fer.zemris.vhdllab.communicaton.AbstractMethod;

import java.util.ArrayList;

/**
 * @author Miro Bezjak
 *
 */
public final class FindUserFilesByUserMethod extends AbstractMethod<ArrayList<Long>> {

	private static final long serialVersionUID = 1L;

	public FindUserFilesByUserMethod() {
		super("find.user.files.by.user");
	}

}