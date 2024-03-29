package cs21120.depq;


/**
 * Priority Double-Ended Queue assignment
 *
 * I implemented a Binary Search Tree because
 * they have a O(log n) time complexity in average for search and delete.
 *
 * Because a keep the references to the most and the lowest value, inspect
 * those values is only O(1) time complexity.
 *
 * -------------------------
 *      Self Evaluation
 * -------------------------
 * The first attempt to resolve this assignment was implementing an Intervale Heap
 * but it didn't work properly and I had problems resolving some parts of the assignment.
 * So I decided to change to a Binary Search Tree implementation. All the methods have
 * Java Docs with is time complexity, Also the main methods (insert, remove, etc) have
 * comments for each line for explaining what they do.
 *
 * I think this work and implementation is at least a 60%.
 *
 */
public class Jov2DEPQ implements DEPQ{

    /** Root node for the three where all the tree starts. */
    private Node root;

    private Comparable least;
    private Comparable most;

    /** Tree size */
    private int size;

    /**
     * Constructor for this class.
     * Just set the root to null and set the size to zero.
     */
    public Jov2DEPQ() {
        this.root = null;
        this.least = null;
        this.most = null;
        this.size = 0;
    }

    /**
     * Know if the new comparable inserted on the tree is the less value thant the actual
     * less value, or the most value from the actual most value
     *
     * Time Complexity: O(1);
     *
     * @param value
     *          The new value inserted on the tree
     */
    private void insertComparable(Comparable value){
        // If the new value is the less thant the actual less value
        // Or is high thant the actual most value
        if(least.compareTo(value) > 0){
            // If is less means is the new least value
            least = value;
        }else if(most.compareTo(value) < 0){
            // If is high means is the most new value
            most = value;
        }
    }

    /**
     *Check if the value deleted from the tree is the actual less or the actual
     * most value, if is, find the new lest or most value on the tree
     *
     * @param value
     *      The value is deleted from the tree
     */
    private void deleteComparable(Comparable value){
        if(least.equals(value)){
            // If is the least, find the new least value on the tree
            least = knowLeast();
        }
        if(most.equals(value)){
            // If the most value, find he new most value on the tree
            most = knowMost();
        }
    }

    /**
     * Find the node with the least value on the tree.
     * Basically keep looking for the bottom left node
     * until his left child is null. That mean, that
     * node is the node with the least value.
     * This method does not remove the value from the tree
     *
     * Time Complexity:
     *      Average: O(log n)
     *      Worst case: O(n)
     *
     * @return
     *      The comparable with the least value on the tree
     */
    private Comparable knowLeast(){
        Node node = root;
        if(node == null){
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node.value;
    }

    /**
     * Find the node with the most value on the tree.
     * Basically keep looking for the bottom right node
     * until his right child is null. That mean, that
     * node is the node with the most value.
     *
     * Time Complexity:
     *      Average: O(log n)
     *      Worst case: O(n)
     *
     * @return
     *      The comparable with the most value on the tree
     */
    private Comparable knowMost(){
        Node node = root;
        if(node == null){
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return node.value;
    }

    /**
     * Inspect the node with the least value.
     *
     * Time Complexity: O(1)
     *
     * @return
     *      The comparable with the least value.
     */
    @Override
    public Comparable inspectLeast() {
        return least;
    }

    /**
     * Inspect the node with the most value.
     *
     * Time Complexity: O(1)
     *
     * @return
     *      The comparable with the most node.
     */
    @Override
    public Comparable inspectMost() {
        return most;
    }

    /**
     * Insert a new value to the tree
     *
     * Time Complexity
     *      Average: O(log n)
     *      Worst Case: O(n)
     *
     * @param value
     *      The value we want insert
     */
    @Override
    public void add(Comparable value) {

        // If the root node is empty, means that the tree is null
        // Create a new node with the value, to be the root node
        // increment the size and exit the methods, we done.
        if (root == null) {
            root = new Node(value);
            least = value;
            most = value;
            size++;
            return;
        }

        // Create two node instance for the searching
        Node insertParentNode = null;
        Node searchTempNode = root;

        // Keep looking until we reach a node with out
        // child, deciding if we go to the left child
        // or the right child depending on the value we
        // want insert to the tree
        while (searchTempNode != null && searchTempNode.value != null) {
            insertParentNode = searchTempNode;
            if (value.compareTo(searchTempNode.value) < 0) {
                searchTempNode = searchTempNode.left;
            } else {
                searchTempNode = searchTempNode.right;
            }
        }

        // Create a new node to insert with the value we want add
        // and set the parent to the parent we search before.
        Node newNode = new Node(value, insertParentNode);

        // Check if the node for insert is not null
        // If is null throw an assert exception
        assert insertParentNode != null;

        // Check the value of the node we want to add to this parent node.
        // If the value is more thant the parent value means this new node
        // go to the right, if not, go to the left
        if (insertParentNode.value.compareTo(newNode.value) > 0) {
            insertParentNode.left = newNode;
        } else {
            insertParentNode.right = newNode;
        }

        // Check if the new value is the new least or the new Most
        insertComparable(value);

        // Increment the size by one
        size++;
    }

    /**
     * Get the least value from the tree and remove from the tree
     *
     * Time Complexity
     *      Average: O(log n)
     *      Worst Case: O(n)
     *
     * @return
     *      The least value from the tree
     */
    @Override
    public Comparable getLeast() {
        Comparable least = inspectLeast();
        delete(least);
        return least;

    }

    /**
     * Get the most value from the tree and remove from the tree
     *
     * Time Complexity
     *      Average: O(log n)
     *      Worst Case: O(n)
     *
     * @return
     *      The most value from the tree
     */
    @Override
    public Comparable getMost() {
        Comparable most = inspectMost();
        delete(most);
        return most;
    }

    /**
     * Know if the tree is empty
     *
     * Time Complexity: O(1)
     *
     * @return
     *      if is empty
     */
    @Override
    public boolean isEmpty() {
        return (size() == 0);
    }

    /**
     * Know the size of the tree
     *
     * Time Complexity: O(1)
     *
     * @return
     *      the size of the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove the value we want if exist in out tree.
     *
     * Time complexity:
     *      Average: O(log n)
     *      Worst case: O(n)
     *
     * @param value
     *          The value needs to be deleted
     */
    private void delete(Comparable value) {

        // Search the node which have the value we want delete. We start
        // from the root of the tree and keep searching until we found the value
        Node deleteNode = root;
        while (deleteNode != null && deleteNode.value != null && deleteNode.value != value) {
            if (value.compareTo(deleteNode.value) < 0) {
                deleteNode = deleteNode.left;
            } else {
                deleteNode = deleteNode.right;
            }
        }

        // Last check before continue.
        // Make sure the node we want delete is not null, and have the same value we want delete
        // If not throw an exception. This can be improve on the interface to set to some methods
        // the possibility to throw exceptions for handle it the problems.
        if (deleteNode == null || !deleteNode.value.equals(value)) {
            throw new RuntimeException("Value not found.");
        }

        // Delete the node by change the references to the other nodes. The Garbage collector
        // just delete the node without reference
        if (deleteNode.left == null) {
            // Does not have any left child
            // set this node to be the right child
            transfer(deleteNode, deleteNode.right);
        } else if (deleteNode.right == null) {
            // Does not have a right child
            // Set this node to be the left child
            transfer(deleteNode, deleteNode.left);
        } else {

            // If have left child and a right we need to deal with his children's
            // Create a instance of the right child
            Node node = deleteNode.right;

            // Keep looking for the right less child with the least node
            while (node.left != null) {
                node = node.left;
            }

            // If the parent of the node found is not the node we want delete
            // transfer the parent of the successorNOde, to his right child.
            // Set the right child of the successor node, to the right child from
            // the node we want delete. Change right child parent to the successor
            // node
            if (node.parent != deleteNode) {
                transfer(node, node.right);
                node.right = deleteNode.right;
                node.right.parent = node;
            }

            // Transfer the node we want delete to the successor node we founded, later
            // set left child a reference to the left child of the node we want delete,
            // and change his left child parent reference to be the successor node we
            // founds.
            transfer(deleteNode, node);
            node.left = deleteNode.left;
            node.left.parent = node;
        }

        // know if the value delete is the most or least value and find the new values
        deleteComparable(value);

        // Decrease the size by one
        size--;
    }

    /**
     * Transfer one node from tree to the place of another node
     * @param node
     *      Node which is go to be replace by the other
     *      node anb removed from the tree
     * @param newNode
     *      The new node to be replaced
     */
    private void transfer(Node node, Node newNode) {
        if (node.parent == null) {
            // Check if the parent of this node is null.
            // If is null means the nodeToReplace is the root node
            // Set the newNode to be the new root node
            this.root = newNode;
        } else if (node == node.parent.left) {
            //If the node we want replace is the left child from his parent
            // is because have a lowest value change the parent less child
            // to be the new node
            node.parent.left = newNode;
        } else {
            // If not means is the right value, so set the parent right node
            // from the node we want replace to be the new node
            node.parent.right = newNode;
        }
        // If the new node is not null
        // set the parent of the new node to be the parent
        // of the node we want replace
        if (newNode != null) {
            newNode.parent = node.parent;
        }
    }

    /**
     * Inner class Node
     * This class hold the value for a node, his parent instance and his left
     * and right child instance
     */
    public class Node {

        /** The comparable value for this node */
        final Comparable value;

        /** His node parent */
        Node parent;

        /** His left child node */
        Node left;

        /** His right child */
        Node right;

        /**
         * Constructor with only passing the comparable value
         * @param value
         *      The comparable value for this node instance
         */
        public Node(Comparable value) {
            this(value, null);
        }

        /**
         * Constructor passing a comparable value and his parent
         * @param value
         *      The comparable value for this node instance
         * @param parent
         *      The parent for this node
         */
        public Node(Comparable value, Node parent) {
            this.value = value;
            this.parent = parent;
            this.left = null;
            this.right = null;
        }

    }
    // End class Node

}