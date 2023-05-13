import java.util.ArrayList;
import java.util.NoSuchElementException;

class FactoryImpl implements Factory {
	private Holder first;
	private Holder last;
	public Integer size;

	public FactoryImpl(Holder first, Holder last, int size) {
		this.first = first;
		this.last = last;
		this.size = size;
	}

	public void addFirst(Product product) {
		// if the list is empty, I added a holder which prev and next holder are null
		// to the beginning index
		if (first == null) {
			Holder firstHolder = new Holder(null, product, null);
			this.first = firstHolder;
			this.last = firstHolder;
			this.size += 1;
		}
		// if the list is not empty, I used Holder first to reach to the first holder
		// and then modified it
		else {
			Holder secondHolder = this.first;
			Holder firstHolder = new Holder(null, product, secondHolder);
			secondHolder.setPreviousHolder(firstHolder);
			this.first = firstHolder;
			this.size += 1;
		}

	}

	public void addLast(Product product) {
		// if the list is empty, I added a holder which prev and next holder are null
		// to the last index, meaning the beginning index
		if (first == null) {
			Holder lastHolder = new Holder(null, product, null);
			this.first = lastHolder;
			this.last = lastHolder;
			this.size += 1;
		}
		// if the list is not empty, I used Holder last to reach to the last holder
		// and then modified it
		else {
			Holder lastHolder = this.last;
			Holder newLastHolder = new Holder(lastHolder, product, null);
			lastHolder.setNextHolder(newLastHolder);
			this.last = newLastHolder;
			this.size += 1;
		}

	}

	public Product removeFirst() throws NoSuchElementException {
		// if the size is 0 meaning there is no holder in the list
		// I just printed the necessary message
		if (this.size == 0) {
			throw new NoSuchElementException("Factory is empty.");
		}
		// else if its not 0, printed the product using the first holder,
		// while changing the secondHolder with the first
		else if (this.size == 1) {
			Holder oldFirst = first;
			this.first = null;
			this.last = null;
			this.size -= 1;
			return oldFirst.getProduct();
		} else {
			Holder second = this.first.getNextHolder();
			second.setPreviousHolder(null);
			Holder oldFirst = first;
			this.first = second;
			this.size -= 1;

			return oldFirst.getProduct();
		}

	}

	public Product removeLast() throws NoSuchElementException {
		// if the size is 0 meaning there is no holder in the list
		// I just printed the necessary message
		if (this.size == 0) {
			throw new NoSuchElementException("Factory is empty.");
		}
		// else if its not 0, printed the product using the last holder,
		// while changing the secondHolder with the first
		else if (this.size == 1) {
			Holder oldLast = last;
			this.last = null;
			this.first = null;
			this.size -= 1;
			return oldLast.getProduct();
		} else {
			Holder newLast = this.last.getPreviousHolder();
			newLast.setNextHolder(null);
			Holder oldLast = last;
			this.last = newLast;
			this.size -= 1;
			return oldLast.getProduct();
		}
	}

	public Product find(int id) throws NoSuchElementException {
		// as always checked if the list is empty
		if (this.size == 0) {
			throw new NoSuchElementException("Product not found.");
		}
		// if its not empty created a holder and a product object to
		// keep track of the place, and then checked if the id's match or not
		// if there's a match printed the product, if not iterated to the next
		// product and holder, if there's one.
		else {
			Holder hold = this.first;
			Product prod = this.first.getProduct();
			while (true) {
				int prodId = prod.getId();
				if (id == prodId) {
					break;
				} else {
					if (hold.getNextHolder() == null) {
						throw new NoSuchElementException("Product not found.");
					} else {
						hold = hold.getNextHolder();
						prod = hold.getProduct();
					}
				}
			}

			return prod;
		}
	}

	public Product update(int id, Integer value) throws NoSuchElementException {
		// again created the holder object to keep track
		// if the holder is not null meaning we're not done in the list
		// got the product Id and checked with the given id, if there's a match
		// then updated the current holder object using setProduct.
		Holder hold = this.first;
		for (int i = 0; i < size; i++) {
			int prodId = hold.getProduct().getId();
			if (prodId == id) {
				Product oldProduct = hold.getProduct();
				Product newProduct = new Product(id, value);
				hold.setProduct(newProduct);
				return oldProduct;
			} else {
				hold = hold.getNextHolder();
			}
		}
		throw new NoSuchElementException("Product not found.");
	}

	public Product get(int index) throws IndexOutOfBoundsException {
		// to get the product in the specfied index I need an index variable
		// then checked if the index is out of bounds using or statement
		// if its not traversed through the linked list to find the specified index
		// therefore the specified product
		int ind = 0;
		Holder hold = this.first;
		if (index > (this.size - 1) || index < 0) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		} else {
			if (index == ind) {
				return hold.getProduct();
			} else {
				hold = hold.getNextHolder();
			}
			ind = ind + 1;
		}
		// I used this because if not I should return something meaningless probably,
		// which is not wanted.
		throw new IndexOutOfBoundsException("Index out of bounds.");
	}

	public void add(int index, Product product) throws IndexOutOfBoundsException {
		// first checked the edge cases, if its the last element and the list is not
		// empty
		// first element and list is not empty and list is empty, since they are
		// different than
		// normal adding
		if (index == size & size != 0) {
			Holder hold = new Holder(this.last, product, null);
			Holder oldLast = this.last;
			oldLast.setNextHolder(hold);
			this.last = hold;
			this.size += 1;
		} else if (index == 0 & size != 0) {
			Holder hold = new Holder(null, product, this.first);
			Holder oldFirst = this.first;
			oldFirst.setPreviousHolder(hold);
			this.first = hold;
			this.size += 1;
		} else if (index == 0 & size == 0) {
			Holder hold = new Holder(null, product, null);
			this.first = hold;
			this.last = hold;
			this.size += 1;
			// then checked the error case to throw an exception
		} else if (index > this.size || index < 0) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		} else {
			// then found the right index to add the holder
			// changed the previous and next holders of prev, current, and new holders
			int indx = 0;
			Holder hold = this.first;
			Holder addHold = new Holder(null, product, null);
			while (true) {
				if (indx == index) {
					Holder prevHolder = hold.getPreviousHolder();
					addHold.setNextHolder(hold);
					hold.setPreviousHolder(addHold);
					prevHolder.setNextHolder(addHold);
					addHold.setPreviousHolder(prevHolder);
					break;
				}
				hold = hold.getNextHolder();
				indx = indx + 1;
			}
			// last incremented the size
			this.size += 1;
		}

	}

	public Product removeIndex(int index) throws IndexOutOfBoundsException {
		// if index is out of bounds, printed that
		if (index > (this.size - 1) || index < 0) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		// else if its not out of bounds first created a current holder and an index to
		// know our cur place
		else {
			Holder curHolder = this.first;
			int indx = 0;
			Holder removedHolder = null;
			while (true) {
				// then created two objects which are previous and next holders
				// only if we are in the index that we want to remove
				if (indx == index) {
					removedHolder = curHolder;
					Holder prevHold = curHolder.getPreviousHolder();
					Holder nextHold = curHolder.getNextHolder();
					// then divided the problem to three, first if we want to remove the first
					// holder
					// we dont have to change the prevHold because there is no previous holder
					if (index == 0) {
						nextHold.setPreviousHolder(null);
						this.first = nextHold;
						this.size -= 1;
						break;
					}
					// same logic like the above just for the last index
					else if (index == this.size - 1) {
						prevHold.setNextHolder(null);
						this.last = prevHold;
						this.size -= 1;
						break;
					}
					// if its in the middle connected prev hold to next
					// and next holder to previous
					else {
						prevHold.setNextHolder(nextHold);
						nextHold.setPreviousHolder(prevHold);
						this.size -= 1;
						break;
					}
				} else {
					curHolder = curHolder.getNextHolder();
					indx = indx + 1;
				}
			}

			return removedHolder.getProduct();
		}
	}

	public Product removeProduct(int value) throws NoSuchElementException {
		// created a new object to keep track, if reached the wanted valued holder
		// created next and prevholder objects in order not to lose them
		// then checked the edge cases, and removed the product, if the code couldn't
		// reach
		// any product then theres exception
		Holder curHolder = this.first;
		int indx = 0;
		if (size > 0) {
			while (true) {
				if (value == curHolder.getProduct().getValue()) {
					Holder nextHolder = curHolder.getNextHolder();
					Holder prevHolder = curHolder.getPreviousHolder();
					if (indx == 0) {
						nextHolder.setPreviousHolder(null);
						this.first = nextHolder;
						this.size -= 1;
						return curHolder.getProduct();
					} else if (indx == this.size - 1) {
						prevHolder.setNextHolder(null);
						this.last = prevHolder;
						this.size -= 1;
						return curHolder.getProduct();
					} else {
						nextHolder.setPreviousHolder(prevHolder);
						prevHolder.setNextHolder(nextHolder);
						this.size -= 1;
						return curHolder.getProduct();
					}
				} else {
					curHolder = curHolder.getNextHolder();
					if (curHolder == null) {
						throw new NoSuchElementException("Product not found.");
					}
					indx = indx + 1;
				}
			}
		} else {
			throw new NoSuchElementException("Product not found.");
		}

	}

	public int filterDuplicates() {
		// created an array list to know whether that product exist or not
		ArrayList<Integer> delVal = new ArrayList<>();
		Holder curHolder = this.first;
		// indx is going to be used for the erased products index
		// and count of remove is going to be returned
		int indx = 0;
		int countOfRemove = 0;
		if (size > 0) {
			while (true) {
				int value = curHolder.getProduct().getValue();
				// if the holders value exists then remove that index
				// if not then add to array list
				if (delVal.contains(value)) {
					removeIndex(indx);
					// index-1 because the list size decremented
					indx = indx - 1;
					countOfRemove = countOfRemove + 1;
				} else {
					delVal.add(value);
				}
				// if at the last holder then break to finish
				if (curHolder.getNextHolder() == null) {
					break;
				}
				curHolder = curHolder.getNextHolder();
				indx = indx + 1;
			}
		}
		return countOfRemove;
	}

	public void reverse() {
		// created temp holder to not lose any objects and current to keep track
		Holder temporary = null;
		Holder curHolder = this.first;
		// just at the start changed last holder to the curHolder
		this.last = curHolder;

		while (curHolder != null) {
			// checked if curHolder is not null because curHolders prevholder is
			// always updated to its next, therefore when the last holder comes
			// loop will exit
			temporary = curHolder.getPreviousHolder();
			curHolder.setPreviousHolder(curHolder.getNextHolder());
			curHolder.setNextHolder(temporary);
			curHolder = curHolder.getPreviousHolder();
		}
		// if its not an edge case, changed the first holder.
		if (size > 1) {
			this.first = temporary.getPreviousHolder();
		}

	}

	public String print() {
		// created a string and added each products tostring to it
		// using a for loop
		Holder curHolder = this.first;
		String factoryLine = "{";
		for (int indx = 0; indx < this.size; indx++) {
			factoryLine += curHolder.toString();
			if (indx != this.size - 1) {
				factoryLine += ",";
			}
			curHolder = curHolder.getNextHolder();
		}
		factoryLine += "}";
		return factoryLine;
	}

}