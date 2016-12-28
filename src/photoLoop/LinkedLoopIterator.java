package photoLoop;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The LinkedLoopIterator class creates an iterator for the LinkedLoop class.
 * 
 * @author Katrina Van Laan
 */

public class LinkedLoopIterator<E> implements Iterator<E> {
	public DblListnode<E> current;

	/**
	 * Constructor for the LinkedLoopIterator class
	 *
	 */
	public LinkedLoopIterator(DblListnode<E> newCurrent) {
		current = newCurrent;// sets current to inserted current
	}

	/**
	 * Returns true if the iteration has more elements, false if not.
	 * 
	 * @return true if the iterator has more elements.
	 */
	@Override
	public boolean hasNext() {// always returns true because this is a circular
								// doubly linked list
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Returns the data for next element in the iteration.
	 * 
	 * @return the data for the next element in the iteration.
	 */
	@Override
	public E next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		E data = current.getData();
		// moves current to next
		current = current.getNext();
		return data;
	}

}
