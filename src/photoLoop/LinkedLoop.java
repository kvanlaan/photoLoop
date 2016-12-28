package photoLoop;

import java.util.Iterator;

/**
 * The LinkedLoop consists of a chain of circular, doubly-linked chain of nodes
 * and implements the LoopADT<E>.
 * 
 * This class has a constructor for creating an empty LinkedLoop, and methods
 * returning an iterator for a LinkedLoop, adding to, obtaining the current data
 * from, removing the current, and adjusting the current forward and backward on
 * the LinkedLoop.
 * 
 * 
 * @author Katrina Van Laan
 */
public class LinkedLoop<E> implements LoopADT<E> {
	// initializing start, current and item count values;
	private DblListnode<E> start;
	private DblListnode<E> current;
	private int itemCount = 0;

	/**
	 * Constructor for the LinkedLoop class
	 *
	 */
	public LinkedLoop() {
		start = null;
		current = null;
		itemCount = 0;
	}

	/**
	 * Adds a new DblListnode with E item as its data, immediately behind the
	 * current node in the LinkedLoop. After the new item has been added, the
	 * new item becomes the current item.
	 * 
	 * @param E
	 *            item, becomes data in new DblListnode
	 */
	@Override
	public void add(E item) {
		// TODO Auto-generated method stub
		if (isEmpty()) {// initialize loop
			start = new DblListnode(item);
			current = start;
			current.setPrev(current);
			current.setNext(current);

		} else {
			DblListnode newNode = new DblListnode(item);// newly added node
			// add node behind current
			current.getPrev().setNext(newNode);
			newNode.setPrev(current.getPrev());
			current.setPrev(newNode);
			newNode.setNext(current);
		}
		previous();// make curren the newly added node
		itemCount++;
	}

	/**
	 * If the LinkedLoop is not empty, returns the data for the current
	 * DblListnode in the LinkedLoop.
	 * 
	 * 
	 * @return E, the data for the current DblListnode if the LinkedLoop is not
	 *         empty.
	 */
	@Override
	public E getCurrent() throws EmptyLoopException {
		// TODO Auto-generated method stub
		if (current == null) {// empty loop test
			throw new EmptyLoopException();
		} else {// returns data
			return current.getData();
		}
	}

	/**
	 * If the LinkedLoop is not empty, removes the current, sets the following
	 * node as the current, and then returns the data for the new current
	 * DblListnode in the LinkedLoop.
	 * 
	 * 
	 * @return E, the data for the new current DblListnode if the LinkedLoop is
	 *         not empty.
	 */
	@Override
	public E removeCurrent() throws EmptyLoopException {
		// TODO Auto-generated method stub
		if (current == null) {// empty loop test
			throw new EmptyLoopException();
		} else {// removes current by unlinking and relinking pointers
			DblListnode<E> tmpOne = current.getNext();
			tmpOne.setPrev(current.getPrev());
			DblListnode<E> tmpTwo = current.getPrev();
			tmpTwo.setNext(current.getNext());
			DblListnode<E> oldCurrent = current;
			current = oldCurrent.getNext();
			itemCount--;// decreases item count
			return current.getData();// returns new current data
		}
	}

	/**
	 * Advances current forward one item resulting in the item that is
	 * immediately after the current item becoming the current item.
	 * 
	 */
	@Override
	public void next() {
		current = current.getNext();
		// TODO Auto-generated method stub

	}

	/**
	 * Moves current backwards one item resulting in the item that is
	 * immediately before the current item becoming the current item.
	 * 
	 */

	@Override
	public void previous() {
		// TODO Auto-generated method stub
		current = current.getPrev();
	}

	/**
	 * Determines if the LinkedLoop is empty, i.e., contains no items.
	 * 
	 * @return boolean true if the LinkedLoop is empty, boolean false if it is
	 *         not;
	 * 
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		if (itemCount > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Returns the itemCount, number of items in the LinkedLoop.
	 * 
	 * @return int representing the itemCount.
	 * 
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return itemCount;
	}

	/**
	 * Iterator for the LinkedLoop class
	 * 
	 * @return Iterator for the LinkedLoop class
	 */

	@Override
	public Iterator<E> iterator() {
		Iterator<E> iter = new LinkedLoopIterator<E>(current);
		return iter;
	}

}
