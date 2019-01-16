


/** Description: This is a helper class to game to represent a "TNode" which is just a node set up kinda like a tree node
* @author Maya Ledvina
* Date: 12/14/18
*/
public class TNode<String> {
	private String item;
	private TNode<String> parent;

	/** constructors*/
	public TNode (String string, TNode<String> p) {
		item = string;
		parent = p;
	}
	
	public TNode (String string) {
		item = string;
		parent = null;
	}
	
	public TNode () {
		item = null;
		parent = null;
	}
	
	/** return the parent in the node
	*@return the parent in the node
	*/
	public TNode (TNode<String> p) {
		parent = p;
	}
	
	/** gets the parent in the node
	*@return the parent in the node
	*/
	public TNode<String> getParent() {
		return parent;
	}
	
	/** sets the parent in the node
	*@return the parent in the node
	*/
	public void setParent(TNode<String> p) {
		this.parent = p;
	}
	
	/** return the item in the node
	*@return the item in the node
	*/
	public String getElement() {
		return item;
	}
	
	/** return the item in the node
	*@return the item in the node
	*/
	public String setElement(String string){
		return item = string;
	}
}