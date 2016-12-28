package photoLoop;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
/**
 * 
 * Title: program 2 //Semester: Fall 2016 //Author: Katrina Van Laan // Email:vanlaan@wisc.edu
 *
 */
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * 
 * Title: Program 2 
 * Files: DblListnode.java, EmptyLoopException.java, Image.java, ImageLoopEditor.java, LinkedLoop.java, 
 * LinkedLoopIterator.java, LinkedLoopADT.java
 * 
 * Semester: Fall 2016 
 * 
 * Author: Katrina Van Laan 
 * Email:vanlaan@wisc.edu
 * Lecturer's Name: Charles Fischer
 *
 */
/**
 * The ImageLoopEditor is a class for prompting and receiving user commands to
 * create and manipulate and LinkedLoop of images.
 * 
 * @author Katrina Van Laan
 */
public class ImageLoopEditor {

	public static void main(String[] args)
			throws EmptyLoopException, FileNotFoundException, UnsupportedEncodingException {
		boolean done = false;
		LinkedLoop<Image> loop = new LinkedLoop<Image>();// new empty linked
															// loop

		Scanner stdin = new Scanner(System.in); // for reading
		// console input
		while (!done) {
			System.out.print("Enter command (? for help)>");
			String input = stdin.nextLine();

			if (input.length() > 0) {
				char choice = input.toLowerCase().charAt(0); // strip off option
																// character
				String remainder = ""; // used to hold the remainder of input
				if (input.length() > 1) {

					remainder = input.substring(1).trim();// trim off any
															// leading or
															// trailing spaces
				}

				switch (choice) {
				case 'l':// l command
					if (remainder.length() > 0) {// requires a string to follow
						File file = new File(remainder);// reading input file
						try {
							Scanner sc = new Scanner(file);// scanner for file
							while (sc.hasNextLine()) {
								String line = sc.nextLine();// new line
								// ArrayList for parsing input
								List<String> details = new ArrayList<String>(Arrays.asList(line.split(" ")));
								String title = "";// need to concat title from
													// multiple values
								if (details.size() < 3) {
									title = "";
								} else if (details.size() < 4) {
									title = title.concat(details.get(2));
								} else if (details.size() < 5) {
									title = title.concat(details.get(2) + " ").concat(details.get(3));
								} else if (details.size() < 6) {
									title = title.concat(details.get(2) + " ").concat(details.get(3) + " ")
											.concat(details.get(4));
								}
								BufferedImage img = null;// testing image exists
															// in images folder
								try {
									img = ImageIO.read(new File("images/" + details.get(0)));
								} catch (IOException e) {
									System.out.println("Warning: filename is not in images folder");
								}
								int dur = 5;// catch for Integer.parseInt
								try {
									dur = Integer.parseInt(details.get(1));
								} catch (NumberFormatException e) {
									System.out.println("Setting a default duration of 5");
								}
								if (title.length() > 0) {// removing quotes

									title = stripQuotes(title);
								}

								Image newImage = new Image(details.get(0), title, dur);// new
																						// image
								loop.add(newImage);// adding image
								loop.next();// current is original file
							}
							sc.close();// close scanner
						} catch (FileNotFoundException e1) {// catching file not
															// found
							System.out.println("unable to load");

						}
					} else {// requires a string to follow
						System.out.println("invalid command");

					}
					break;

				case 's':// s command
					if (!loop.isEmpty()) {// testing for empty loop
						// test to see if file already exists for file
						// overwritten warning
						File fileTest = new File(remainder);
						if (fileTest.exists()) {
							System.out.println("warning: file already exists, will be overwritten");
						}
						try {
							// writes new file
							PrintWriter writer = new PrintWriter(remainder, "UTF-8");
							Iterator<Image> sItr = loop.iterator();
							for (int i = 0; i < loop.size(); i++) {
								writer.println(sItr.next());
							}
							writer.close();
						} catch (IOException e) {// save error catch
							System.out.println("unable to save");
						}

					} else {
						System.out.println("no images to save");
					}
					break;

				case 'd':// d command
					if (!loop.isEmpty()) {// empty test
						for (int i = 0; i < loop.size(); i++) {
							// prints data for current of each
							System.out.println(loop.getCurrent());
							loop.next();
						}
					} else {
						System.out.println("no images");
					}
					break;

				case 'p':// p command, displays current image
					if (!loop.isEmpty()) {
						try {
							loop.getCurrent().displayImage();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else {
						System.out.println("no images");
					}
					break;

				case 'b':// b command, moves current back one position
					if (!loop.isEmpty()) {// empty test
						System.out.println("    " + loop.getCurrent() + "    ");
						loop.previous();
						System.out.println("--> " + loop.getCurrent() + " <--");
						loop.previous();// moving back to print previous
						System.out.println("    " + loop.getCurrent() + "    ");
						loop.next();// moving forward to correct place
					} else {
						System.out.println("no images");
					}
					break;
				case 'f':// f command, moves current forward one position
					System.out.println("    " + loop.getCurrent() + "    ");// prints
					loop.next();// moves current forward
					System.out.println("--> " + loop.getCurrent() + " <--");// prints
					loop.next();// moving forward to print next
					System.out.println("    " + loop.getCurrent() + "    ");
					loop.previous();// moves backward to the correct place
					break;
				case 'j':// command j
					int num = 0;// initialize num
					if (!loop.isEmpty()) {// empty test
						try {// try catch for parseInt
							num = Integer.parseInt(remainder);
							if (num >= 0) {// if num is positive
								for (int i = 0; i <= num - 1; i++) {
									if (i == num - 1) {// goes 1 previous
										System.out.println("    " + loop.getCurrent() + "    ");
									} else {
										loop.next();
									}
								}
							} else {// if num is negative
								for (int x = 0; x >= num - 1; x--) {
									if (x == num - 1) {// goes 1 previous
										System.out.println("    " + loop.getCurrent() + "    ");
									} else {
										loop.previous();
									}
								}
							}
							loop.next();// moves to new current
							System.out.println("--> " + loop.getCurrent() + " <--");// prints
							loop.next();// moves to print next
							System.out.println("    " + loop.getCurrent() + "    ");
							loop.previous();// moves back to correct place

						} catch (NumberFormatException e) {
							System.out.println("invalid command");
						}
					} else {
						System.out.println("no images");
					}
					break;
				case 't':// test command, iterates through and displays images
					Iterator<Image> tItr = loop.iterator();
					for (int i = 0; i < loop.size(); i++) {
						try {
							tItr.next().displayImage();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				case 'r':// remove image
					if (!loop.isEmpty()) {// empty test
						if (loop.size() > 2) {// only print previous if size > 2
							loop.previous();
							System.out.println("    " + loop.getCurrent() + "    ");
							loop.next();
						}
						loop.removeCurrent();// remove
						if (!loop.isEmpty()) {// if not empty, print new current
							System.out.println("--> " + loop.getCurrent() + " <--");
							loop.next();
							System.out.println("    " + loop.getCurrent() + "    ");
							loop.previous();
						} else {// if empty, print no image
							System.out.println("no images");
						}
					} else {
						System.out.println("no images");
					}
					break;
				case 'a':
					BufferedImage aImg = null;// image exists warning test
					try {
						aImg = ImageIO.read(new File("images/" + remainder));
					} catch (IOException e) {
						System.out.println("Warning: filename is not in images folder");
					}
					Image aImage = new Image(remainder, "", 5);// new image
					// only print previous if size > 2, size is about to
					// increase to 2
					if (!loop.isEmpty() && loop.size() > 1) {
						System.out.println("    " + loop.getCurrent() + "    ");
						loop.next();
					}
					loop.add(aImage);// add new image
					System.out.println("--> " + loop.getCurrent() + " <--");
					if (!loop.isEmpty() && loop.size() > 1) {// only print next
																// if size > 1
						loop.next();
						System.out.println("    " + loop.getCurrent() + "    ");
						loop.previous();
					}

					break;

				case 'i':
					BufferedImage iImg = null;// image exists warning test
					try {
						iImg = ImageIO.read(new File("images/" + remainder));
					} catch (IOException e) {
						System.out.println("Warning: filename is not in images folder");
					}
					Image iImage = new Image(remainder, "", 5);// new image
					loop.add(iImage);

					if (!loop.isEmpty() && loop.size() > 2) {// only print
																// previous if
																// size > 2
						loop.previous();
						System.out.println("    " + loop.getCurrent() + "    ");
						loop.next();
					}

					System.out.println("--> " + loop.getCurrent() + " <--");
					if (!loop.isEmpty() && loop.size() > 1) {// only print next
						// if size > 1
						loop.next();
						System.out.println("    " + loop.getCurrent() + "    ");
						loop.previous();
					}
					break;
				case 'e':
					if (!loop.isEmpty()) {// empty test
						// loop.previous();
						// System.out.println(loop.getCurrent());
						// loop.next();
						Image eCurrent = loop.getCurrent();// new image
						if (remainder.length() > 0) {// removing quotes
							remainder = stripQuotes(remainder);
						}
						eCurrent.setTitle(remainder);// setting title
						loop.removeCurrent();// removing current
						loop.add(eCurrent);// replacing with new
						// printing context
						loop.previous();
						System.out.println("    " + loop.getCurrent() + "    ");
						loop.next();
						System.out.println("--> " + loop.getCurrent() + " <--");
						loop.next();
						System.out.println("    " + loop.getCurrent() + "    ");
						loop.previous();
					} else {
						System.out.println("no images");
					}
					break;
				case 'c':// c command
					if (!loop.isEmpty()) {// empty test
						Iterator<Image> cItr = loop.iterator();// iterator to
																// search
						boolean found = false;
						// parsing so that " " are irrelevant
						remainder = stripQuotes(remainder);
						Image newCurrent = new Image("", "", 5);
						for (int i = 0; i < loop.size(); i++) {
							Image item = loop.getCurrent();
							String itemTitle = item.getTitle();
							// parsing so that " " are irrelevant
							if (remainder.equals(itemTitle)) {// find match
								newCurrent = item;
								loop.previous();
								// printing context
								System.out.println("    " + loop.getCurrent() + "    ");
								loop.next();
								System.out.println("--> " + loop.getCurrent() + " <--");
								loop.next();
								System.out.println("    " + loop.getCurrent() + "    ");
								loop.previous();
								break;
							}
							loop.next();
						}
						if (found != true) {

							System.out.println("not found");
						}
					} else {
						System.out.println("no images");
					}
					break;

				case 'u':// duration command
					int numU = 0;
					if (!loop.isEmpty()) {// empty test
						try {// parseInt test
							numU = Integer.parseInt(remainder);
							loop.previous();
							System.out.println("    " + loop.getCurrent() + "    ");
							loop.next();
							Image uCurrent = loop.getCurrent();
							uCurrent.setDuration(numU);// setting duration
							loop.removeCurrent();
							loop.add(uCurrent);
							System.out.println("--> " + loop.getCurrent() + " <--");
							loop.next();
							System.out.println("    " + loop.getCurrent() + "    ");
							loop.previous();
						} catch (NumberFormatException e) {
							System.out.println("invalid command");

						}
					} else {
						System.out.println("no images");
					}
					break;

				case 'x':
					done = true;
					System.out.println("exit");
					break;

				case '?':
					printOptions();

					break;

				default: // ignore any unknown commands
					// handling for bad input
					System.out.println("invalid command");
					break;
				}
			}

		}

		stdin.close();

	}

	/**
	 * Helper for Stripping quotes off titles
	 */

	private static String stripQuotes(String title) {
		if (title.charAt(0) == '"') {
			title = title.substring(1);
		}
		if (title.charAt(title.length() - 1) == '"') {
			title = title.substring(0, title.length() - 1);
		}
		return title;
	}

	/**
	 * Prints the list of command options along with a short description of one.
	 */
	private static void printOptions() {
		System.out.println("s (save)");
		System.out.println("l (load)");
		System.out.println("d (display)");
		System.out.println("p (picture)");
		System.out.println("f (forward)");
		System.out.println("b (backward)");
		System.out.println("j (jump)");
		System.out.println("t (test)");
		System.out.println("r (remove)");
		System.out.println("a (add after)");
		System.out.println("i (insert before)");
		System.out.println("e (retitle)");
		System.out.println("c (contains)");
		System.out.println("u (update)");
		System.out.println("x (exit)");
	}

}