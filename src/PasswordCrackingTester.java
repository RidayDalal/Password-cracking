package src;

import java.util.NoSuchElementException;

/**
 * This class contains methods that test the implementation of
 * various methods in src.Password, src.PasswordNode, and src.PasswordStorage class
 * and returns appropriate boolean values true if it passes, else false
 */
public class PasswordCrackingTester {

    /**
     * Validates the constructor and accessor methods of src.PasswordStorage, specifically the
     * getComparisonCriteria(), size(), and isEmpty() methods, as well as accessing the
     * protected data field root.
     *
     * @return true if the basic accessor methods work as expected, false otherwise
     */
    public static boolean testBasicPasswordStorageMethods() {

        //1. testing the basic storage methods with OCCURENCE attribute
        {
            PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);

            if (!bst.isEmpty()) {
                return false;
            }

            if (bst.root != null) {
                return false;
            }
            if (bst.getComparisonCriteria() != Attribute.OCCURRENCE) {
                return false;
            }
            if (bst.size() != 0) {
                return false;
            }
        }

        //2. testing the basic storage methods with STRENGTH_RATING attribute
        {
            PasswordStorage bst = new PasswordStorage(Attribute.STRENGTH_RATING);

            if (!bst.isEmpty()) {
                return false;
            }

            if (bst.root != null) {
                return false;
            }
            if (bst.getComparisonCriteria() != Attribute.STRENGTH_RATING) {
                return false;
            }
            if (bst.size() != 0) {
                return false;
            }
        }

        //3. testing the basic storage methods with
        {
            PasswordStorage bst = new PasswordStorage(Attribute.HASHED_PASSWORD);

            if (!bst.isEmpty()) {
                return false;
            }

            if (bst.root != null) {
                return false;
            }
            if (bst.getComparisonCriteria() != Attribute.HASHED_PASSWORD) {
                return false;
            }
            if (bst.size() != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates the src.Password class compareTo() method. Create at least two DIFFERENT
     * src.Password objects and compare them on each of the src.Attribute values.
     *
     * @return true if src.Password's compareTo() works as expected, false otherwise
     */
    public static boolean testPasswordCompareTo() {

        Password firstPassword = new Password("HenryCx76&&", 120);
        Password secondPassword = new Password("GabiElbar45%", 90);
        Password thirdPassword = new Password("iloveyou", 200);
        Password fourthPassword = new Password("password", 90);

        {
            // 1. Based on occurence
            int firstActual = firstPassword.compareTo(secondPassword, Attribute.OCCURRENCE);
            int secondActual = secondPassword.compareTo(thirdPassword, Attribute.OCCURRENCE);
            int thirdActual = secondPassword.compareTo(fourthPassword, Attribute.OCCURRENCE);
            if (!(firstActual > 0 && secondActual < 0 && thirdActual == 0)) {
                return false;
            }
        }

        {
            // 2. Based on strength rating
            int actual = firstPassword.compareTo(secondPassword, Attribute.STRENGTH_RATING);
            int secondActual = secondPassword.compareTo(thirdPassword, Attribute.STRENGTH_RATING);
            if (!(actual < 0 && secondActual > 0)) {
                return false;
            }
        }

        {
            // 3. Based on hashed password
            int actual = firstPassword.compareTo(secondPassword, Attribute.HASHED_PASSWORD);
            if (actual >= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates the incomplete methods in src.PasswordNode, specifically isLeafNode(),
     * numberOfChildren(), hasLeftChild() and hasRightChild().
     *
     * @return true if the status methods of src.PasswordNode work as expected, false otherwise
     */
    public static boolean testNodeStatusMethods() {

        {
            // 1. test for isLeafNode() method
            PasswordNode someNode = new PasswordNode(new Password("HenryCx76&&", 97));
            PasswordNode otherNode = new PasswordNode(new Password("somePass665", 100), someNode, null);

            //someNode has NO left and NO right child
            boolean actual = someNode.isLeafNode();
            boolean expected = true;

            if (actual != expected) {
                return false;
            }

            //otherNode has left child but NO right child
            boolean otherActual = otherNode.isLeafNode();
            boolean otherExpected = false;

            if (otherExpected != otherActual) {
                return false;
            }
        }

        {
            // 2. test for hasLeftChild() method
            PasswordNode leftNode = new PasswordNode(new Password("GabriElbar45%%", 76));
            PasswordNode rightNode = new PasswordNode(new Password("paBlOESx9?7", 43));
            PasswordNode someNode = new PasswordNode(new Password("HenryCx76&&", 57), leftNode, rightNode);
            PasswordNode otherNode = new PasswordNode(new Password("password", 76), null, rightNode);

            //someNode has a left child
            boolean actual = someNode.hasLeftChild();
            boolean expected = true;

            if (actual != expected) {
                return false;
            }

            //otherNode DOES NOT have a left child
            boolean otherActual = otherNode.hasLeftChild();
            boolean otherExpected = false;

            if (otherExpected != otherActual) {
                return false;
            }
        }

        {
            // 3. test for hasRightChild() method
            PasswordNode leftNode = new PasswordNode(new Password("GabriElbar45%%", 76));
            PasswordNode rightNode = new PasswordNode(new Password("paBlOESx9?7", 43));
            PasswordNode someNode = new PasswordNode(new Password("HenryCx76&&", 55), leftNode, rightNode);
            PasswordNode otherNode = new PasswordNode(new Password("password", 76), leftNode, null);

            //someNode has a right child
            boolean actual = someNode.hasRightChild();
            boolean expected = true;

            if (actual != expected) {
                return false;
            }

            //otherNode DOES NOT have a right child
            boolean otherActual = otherNode.hasRightChild();
            boolean otherExpected = false;

            if (otherExpected != otherActual) {
                return false;
            }
        }

        {
            // 4. test for numberOfChildren() method
            PasswordNode rightNode = new PasswordNode(new Password("paBlOESx9?7", 43));
            PasswordNode someNode = new PasswordNode(new Password("HenryCx76&&", 97), null, rightNode);
            int actual = someNode.numberOfChildren();
            int expected = 1;

            if (actual != expected) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates the correctness of the toString() method of the src.PasswordStorage class
     * taking different cases into consideration:
     * 1. Empty tree
     * 2. Single-node tree
     * 3. Multi-node tree
     *
     * @return true if all test cases pass, else false
     */
    public static boolean testToString() {
        try {
            PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);

            // empty is empty string
            String expected = "";
            String actual = bst.toString();
            if (!actual.equals(expected)) {
                System.out.println("toString() does not return the proper value on an empty tree!");
                return false;
            }

            // size one only returns 1 thing
            Password p = new Password("1234567890", 15000);
            PasswordNode rootNode = new PasswordNode(p);

            bst.root = rootNode; // here I am manually building the tree by editing the root node
            // directly to be the node of my choosing

            expected = p.toString() + "\n";
            actual = bst.toString();

            if (!actual.equals(expected)) {
                System.out.println("a");
                return true;
            }



            // big tree returns in-order traversal
            Password p2 = new Password("test", 500);
            Password p3 = new Password("iloveyou", 765);
            Password p4 = new Password("qwerty", 250);
            Password p5 = new Password("admin", 1002);
            Password p6 = new Password("password", 2232);
            Password p7 = new Password("abc123", 2090);

            PasswordNode p4Node = new PasswordNode(p4);
            PasswordNode p3Node = new PasswordNode(p3);
            PasswordNode p7Node = new PasswordNode(p7);
            PasswordNode p6Node = new PasswordNode(p6, p7Node, null);
            PasswordNode p5Node = new PasswordNode(p5, null, p6Node);
            PasswordNode p2Node = new PasswordNode(p2, p4Node, p3Node);
            rootNode = new PasswordNode(p, p2Node, p5Node);
            bst.root = rootNode;

            expected = p4.toString() + "\n" + p2.toString() + "\n" + p3.toString() + "\n" + p.toString()
                    + "\n" + p5.toString() + "\n" + p7.toString() + "\n" + p6.toString() + "\n";
            actual = bst.toString();

            if (!actual.equals(expected)) {
                System.out.println("b");
                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Validates the correctness of IsValidBST() method of the src.PasswordStorage class
     *
     * @return true if all test cases pass, else false
     */
    public static boolean testIsValidBST() {
        try {
            PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);

            // empty tree is a valid bst
            /*
             * String expected = ""; String actual = bst.toString();
             */
            if (!bst.isValidBST()) {
                System.out.println("isValidBST() says that an empty tree is not a valid BST!");
                return false;
            }

            // size one is a bst
            Password p = new Password("1234567890", 1000);
            PasswordNode rootNode = new PasswordNode(p);

            bst.root = rootNode; // here I am manually building the tree by editing the root node
            // directly to be the node of my choosing

            if (!bst.isValidBST()) {
                System.out.println("isValidBST() says that a tree of size 1 is not a valid BST!");
                return false;
            }

            Password p2 = new Password("test", 500);
            Password p3 = new Password("iloveyou", 765);
            Password p4 = new Password("qwerty", 250);
            Password p5 = new Password("admin", 1002);
            Password p6 = new Password("password", 2232);
            Password p7 = new Password("abc123", 2090);

            // works on indentifying small obviously invalid tree
            PasswordNode p7Node = new PasswordNode(p7);
            PasswordNode p3Node = new PasswordNode(p3);
            rootNode = new PasswordNode(p, p7Node, p3Node);
            bst.root = rootNode;
            if (bst.isValidBST())
                return false;

            // tree with only one subtree being valid, other subtree has a violation a couple layers deep


            PasswordNode p4Node = new PasswordNode(p4);
            p7Node = new PasswordNode(p7);
            p3Node = new PasswordNode(p3);
            PasswordNode p6Node = new PasswordNode(p6, null, p7Node);
            PasswordNode p5Node = new PasswordNode(p5, null, p6Node);
            PasswordNode p2Node = new PasswordNode(p2, p4Node, p3Node);
            rootNode = new PasswordNode(p, p2Node, p5Node);
            bst.root = rootNode;

            if (bst.isValidBST()) {
                System.out
                        .println("isValidBST() says that a tree with only one valid subtree is a valid bst");
                return false;
            }


            // works on valid large tree
            p4Node = new PasswordNode(p4);
            p3Node = new PasswordNode(p3);
            p7Node = new PasswordNode(p7);
            p6Node = new PasswordNode(p6, p7Node, null);
            p5Node = new PasswordNode(p5, null, p6Node);
            p2Node = new PasswordNode(p2, p4Node, p3Node);
            rootNode = new PasswordNode(p, p2Node, p5Node);
            bst.root = rootNode;

            if (!bst.isValidBST())
                return false;

            PasswordNode one = new PasswordNode(p4);
            PasswordNode three = new PasswordNode(p3, one, null);
            PasswordNode two = new PasswordNode(p2, null, three);
            bst.root = two;

            if (bst.isValidBST()) {
                System.out.println("bad bst is valid");
                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Validates the correctness of the lookup() method in the src.PasswordStorage class
     * taking into consideration all cases on each of the three attributes:
     * 1. Desired src.Password is already present in the tree
     * 2. Desired src.Password is NOT present in the tree
     *
     * @return true if all test cases pass, else false
     */
    public static boolean testLookup() {

        // 1. testing based on OCCURRENCE attribute
        {
            PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);

            Password firstPassword = new Password("Mikehunt123", 200);
            Password secondPassword = new Password("Mikehunt456", 202);
            Password thirdPassword = new Password("Mikehunt789", 212);
            Password fourthPassword = new Password("Mikehunt098", 204);

            Password passwordToBeFound = new Password("Mikehunt156", 208);

            bst.addPassword(firstPassword);
            bst.addPassword(secondPassword);
            bst.addPassword(thirdPassword);
            bst.addPassword(fourthPassword);

            {
                // Desired password is present in the BST
                try {
                    Password expected = thirdPassword;
                    Password actual = bst.lookup(thirdPassword);
                    if (actual != expected) {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }

            {
                // Desired password is NOT present in the BST
                try {
                    Password expected = null;
                    Password actual = bst.lookup(passwordToBeFound);
                } catch (Exception e) {
                    return false;
                }
            }
        }

        // 2. testing based on HASHED_PASSWORD attribute
        {
            PasswordStorage bst = new PasswordStorage(Attribute.HASHED_PASSWORD);

            Password firstPassword = new Password("Mikehunt123", 200);
            Password secondPassword = new Password("BenDover887", 202);
            Password thirdPassword = new Password("HughJass008", 212);
            Password fourthPassword = new Password("Michelle018", 204);

            Password passwordToBeFound = new Password("HoverBoard056", 208);

            bst.addPassword(secondPassword);
            bst.addPassword(firstPassword);
            bst.addPassword(fourthPassword);
            bst.addPassword(thirdPassword);

            //Desired password is already present in the BST
            try {
                Password expected = thirdPassword;
                Password actual = bst.lookup(thirdPassword);

                if (actual != expected) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }

            //Desired password is NOT present in the BST
            try {
                Password expected = null;
                Password actual = bst.lookup(passwordToBeFound);

                if (actual != expected) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }

        {
            // 2. testing based on STRENGTH_RATING attribute
            {
                PasswordStorage bst = new PasswordStorage(Attribute.STRENGTH_RATING);

                Password firstPassword = new Password("Mikehunt123", 200);
                Password secondPassword = new Password("BenDor887?*", 202);
                Password thirdPassword = new Password("HughJass*&", 212);
                Password fourthPassword = new Password("Mich", 204);

                Password passwordToBeFound = new Password("password", 208);

                bst.addPassword(secondPassword);
                bst.addPassword(firstPassword);
                bst.addPassword(fourthPassword);
                bst.addPassword(thirdPassword);

                //Desired password is already present in the BST
                try {
                    Password expected = thirdPassword;
                    Password actual = bst.lookup(thirdPassword);

                    if (actual != expected) {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }

                //Desired password is NOT present in the BST
                try {
                    Password expected = null;
                    Password actual = bst.lookup(passwordToBeFound);

                    if (actual != expected) {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Tests the implementation of the addPassword() method in the passwordStorage class
     * taking into consideration all scenarios on all three attributes:
     * 1. Empty tree
     * 2. Non-empty tree
     * 3. Trying to add a node already existing in the tree
     *
     * @return true if all test cases pass, else false
     */
    public static boolean testAddPassword() {

        {
            // 1. Testing based on OCCURRENCE attribute
            PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);

            Password firstPassword = new Password("password1", 746);
            Password secondPassword = new Password("password2", 500);
            Password thirdPassword = new Password("password3", 986);
            Password fourthPassword = new Password("password4", 436);
            Password fifthPassword = new Password("password5", 534);

            //testing addPassword() on empty tree
            try {
                bst.addPassword(firstPassword);

                int actual = bst.size();
                int expected = 1;

                if (actual != expected) {
                    return false;
                }

            } catch (IllegalArgumentException e) {
                return false;
            } catch (Exception e) {
                return false;
            }

            // testing addPassword() on a non-empty tree
            try {
                bst.addPassword(thirdPassword);
                bst.addPassword(fourthPassword);
                bst.addPassword(secondPassword);

                String actualString = bst.toString();
                String expectedString = fourthPassword + "\n" + secondPassword + "\n" + firstPassword
                        + "\n" + thirdPassword + "\n";

                if (!actualString.equals(expectedString)) {
                    return false;
                }

                int actual = bst.size();
                int expected = 4;

                if (actual != expected) {
                    return false;
                }

            } catch (IllegalArgumentException e) {
                return false;
            } catch(Exception e) {
                return false;
            }

            //trying to add a node already in the tree
            try {
                bst.addPassword(fifthPassword);
                bst.addPassword(secondPassword);
                return false;
            } catch (IllegalArgumentException e) {

            } catch (Exception e) {
                return false;
            }
        }

        {
            // 2. Testing based on STRENGTH_RATING attribute
            PasswordStorage bst = new PasswordStorage(Attribute.STRENGTH_RATING);

            Password firstPassword = new Password("henryCavill", 746);
            Password secondPassword = new Password("JaggA009", 500);
            Password thirdPassword = new Password("password", 986);
            Password fourthPassword = new Password("Mik3Hu00?", 436);
            Password fifthPassword = new Password("HELL@", 534);

            //testing addPassword() on empty tree
            try {
                bst.addPassword(secondPassword);

                int actual = bst.size();
                int expected = 1;

                if (actual != expected) {
                    return false;
                }

            } catch (IllegalArgumentException e) {
                return false;
            } catch (Exception e) {
                return false;
            }

            //testing addPassword() on a non-empty tree
            try {
                bst.addPassword(fifthPassword);
                bst.addPassword(thirdPassword);
                bst.addPassword(fourthPassword);

                String actualString = bst.toString();
                String expectedString = fifthPassword + "\n" + thirdPassword + "\n" + secondPassword
                        + "\n" + fourthPassword + "\n";

                if (!actualString.equals(expectedString)) {
                    return false;
                }

                int actual = bst.size();
                int expected = 4;

                if (actual != expected) {
                    return false;
                }

            } catch (IllegalArgumentException e) {
                return false;
            } catch (Exception e) {
                return false;
            }

            //trying to add a node already in the tree
            try {
                bst.addPassword(firstPassword);
                bst.addPassword(fifthPassword);
                return false;
            } catch (IllegalArgumentException e) {

            } catch (Exception e) {
                return false;
            }
        }

        {
            // 3. Testing based on HASHED_PASSWORD attribute
            PasswordStorage bst = new PasswordStorage(Attribute.HASHED_PASSWORD);

            Password firstPassword = new Password("henryCavill", 746);
            Password secondPassword = new Password("JaggA009", 500);
            Password thirdPassword = new Password("password", 986);
            Password fourthPassword = new Password("Mik3Hu00?", 436);
            Password fifthPassword = new Password("HELL@", 534);

            //testing addPassword() method on an empty tree
            try {
                bst.addPassword(thirdPassword);

                int actual = bst.size();
                int expected = 1;

                if (actual != expected) {
                    return false;
                }

            } catch (IllegalArgumentException e) {
                return false;
            } catch (Exception e) {
                return false;
            }

            //testing addPassword() method on a non-empty tree
            try {
                bst.addPassword(fifthPassword);
                bst.addPassword(secondPassword);
                bst.addPassword(firstPassword);

                String actualString = bst.toString();
                String expectedString = thirdPassword + "\n" + fifthPassword + "\n" + firstPassword
                        + "\n" + secondPassword + "\n";

                if (!actualString.equals(expectedString)) {
                    return false;
                }

                int actual = bst.size();
                int expected = 4;

                if (actual != expected) {
                    return false;
                }

            } catch (IllegalArgumentException e) {
                return false;
            } catch (Exception e) {
                return false;
            }

            //trying to add a node already in the tree
            try {
                bst.addPassword(fourthPassword);
                bst.addPassword(secondPassword);
                return false;
            } catch (IllegalArgumentException e) {

            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests the correctness of the removePassword() method.
     * Tests all the cases including:
     * 1. Removing a leaf node from the tree.
     * 2. Removing a child with one child node.
     * 3. Removing a child with two child nodes.
     * 4. Removing a node NOT present in the tree.
     *
     * @return true if all test cases pass, else false
     */
    public static boolean testRemovePassword() {

        {
            // removing a leaf Node from left tree
            try {
                PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);
                Password firstPassword = new Password("henryCavill", 746);
                Password secondPassword = new Password("JaggA009", 747);
                Password thirdPassword = new Password("password", 748);
                Password fourthPassword = new Password("Mik3Hu00?", 749);
                Password fifthPassword = new Password("HELL@", 750);
                Password sixthPassword = new Password("src.Password@6", 206);

                bst.addPassword(fourthPassword);
                bst.addPassword(secondPassword);
                bst.addPassword(fifthPassword);
                bst.addPassword(firstPassword);
                bst.addPassword(thirdPassword);
                bst.addPassword(sixthPassword);


                bst.removePassword(secondPassword);


                String expected = "src.Password@6(954832c90e14d0ff2186c39c977f71a892c7d108): 206 [21.0]\n" +
                        "henryCavill(a1ef13ac4579be720f0be883a62bb2fb4ed09ed6): 746 [14.5]\n" +
                        "password(5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8): 748 [9.75]\n" +
                        "Mik3Hu00?(6b37dd72b0afb33fc8ab9ed085eb59fb7b18f643): 749 [16.0]\n" +
                        "HELL@(9a65858f625a30be649102a5aa0920c82b8118c6): 750 [8.5]\n";


                String actual = bst.toString();

                if(!expected.equals(actual)) {
                    return false;
                }

                if(bst.size() != 5) return false;


            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        { // removing a leaf node from the right tree
            try {
                PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);
                Password firstPassword = new Password("henryCavill", 746);
                Password secondPassword = new Password("JaggA009", 747);
                Password thirdPassword = new Password("password", 748);
                Password fourthPassword = new Password("Mik3Hu00?", 749);
                Password fifthPassword = new Password("HELL@", 750);
                Password sixthPassword = new Password("src.Password@6", 206);

                bst.addPassword(fourthPassword);
                bst.addPassword(secondPassword);
                bst.addPassword(fifthPassword);
                bst.addPassword(firstPassword);
                bst.addPassword(thirdPassword);


                bst.removePassword(sixthPassword);
                System.out.println("b");

                return false;

            } catch (NoSuchElementException e){

            } catch (Exception e) {
                return false;
            }

        }

        { // removing a node with single child
            try {
                PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);
                Password firstPassword = new Password("henryCavill", 746);
                Password secondPassword = new Password("JaggA009", 747);
                Password thirdPassword = new Password("password", 748);
                Password fourthPassword = new Password("Mik3Hu00?", 749);
                Password fifthPassword = new Password("HELL@", 750);
                Password sixthPassword = new Password("src.Password@6", 206);

                bst.addPassword(fourthPassword);
                bst.addPassword(secondPassword);
                bst.addPassword(fifthPassword);
                bst.addPassword(firstPassword);
                bst.addPassword(thirdPassword);
                bst.addPassword(sixthPassword);

                bst.removePassword(fifthPassword);

                String actual = bst.toString();
                //System.out.println(actual);

                String expected = "src.Password@6(954832c90e14d0ff2186c39c977f71a892c7d108): 206 [21.0]\n" +
                        "henryCavill(a1ef13ac4579be720f0be883a62bb2fb4ed09ed6): 746 [14.5]\n" +
                        "JaggA009(fdbfdadd6b996e6d8731e3d627a5467ee789eaeb): 747 [13.25]\n" +
                        "password(5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8): 748 [9.75]\n" +
                        "Mik3Hu00?(6b37dd72b0afb33fc8ab9ed085eb59fb7b18f643): 749 [16.0]\n";

                if(!actual.equals(expected)) {
                    return false;
                }

            } catch (Exception e) {
                return false;
            }

        }

        { // removing a node based on a different src.Attribute
            try {

                PasswordStorage bst = new PasswordStorage(Attribute.STRENGTH_RATING);
                Password firstPassword = new Password("IL0veyo^", 609);
                Password secondPassword = new Password("h3nryCav!LL", 610);
                Password thirdPassword = new Password("PAssWoRD08", 611);
                Password fourthPassword = new Password("Riday06&&%", 612);
                Password fifthPassword = new Password("!!hellow!@#", 613);

                bst.addPassword(fourthPassword);
                bst.addPassword(thirdPassword);
                bst.addPassword(fifthPassword);
                bst.addPassword(secondPassword);
                bst.addPassword(firstPassword);

                bst.removePassword(fourthPassword);

            } catch (Exception e){
                return false;
            }
        }

        {
            // removing a root Node
            try {
                PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);
                Password firstPassword = new Password("henryCavill", 746);
                Password secondPassword = new Password("JaggA009", 747);
                Password thirdPassword = new Password("password", 748);
                Password fourthPassword = new Password("Mik3Hu00?", 749);
                Password fifthPassword = new Password("HELL@", 750);

                bst.addPassword(fourthPassword);
                bst.addPassword(secondPassword);
                bst.addPassword(fifthPassword);
                bst.addPassword(firstPassword);
                bst.addPassword(thirdPassword);

                bst.removePassword(fourthPassword);

                String actual = bst.toString();

                String expected = "henryCavill(a1ef13ac4579be720f0be883a62bb2fb4ed09ed6): 746 [14.5]\n" +
                        "JaggA009(fdbfdadd6b996e6d8731e3d627a5467ee789eaeb): 747 [13.25]\n" +
                        "password(5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8): 748 [9.75]\n" +
                        "HELL@(9a65858f625a30be649102a5aa0920c82b8118c6): 750 [8.5]\n";

                if(!actual.equals(expected)){
                    return false;
                }

                if(bst.size() != 4) return false;

            } catch (Exception e) {
                return false;
            }
        }

        return true;

    }

    /**
     * The main method of the src.PasswordCrackingTester class that runs the
     * runAllTests() methods
     *
     * @param args
     */
    public static void main(String[] args) {
        runAllTests();
    }

    /**
     * This method runs all the tester methods of this class and prints out whether
     * each test method has passed or not
     *
     * @return true if all the tester methods run, else false
     */
    public static boolean runAllTests() {
        boolean compareToPassed = testPasswordCompareTo();
        boolean nodeStatusPassed = testNodeStatusMethods();
        boolean basicMethodsPassed = testBasicPasswordStorageMethods();
        boolean toStringPassed = testToString();
        boolean isValidBSTPassed = testIsValidBST();
        boolean lookupPassed = testLookup();
        boolean addPasswordPassed = testAddPassword();
        boolean removePasswordPassed = testRemovePassword();

        System.out.println("src.Password compareTo: " + (compareToPassed ? "PASS" : "FAIL"));
        System.out.println("src.PasswordNode Status Methods: " + (nodeStatusPassed ? "PASS" : "FAIL"));
        System.out.println("src.PasswordStorage Basic Methods: " + (basicMethodsPassed ? "PASS" : "FAIL"));
        System.out.println("src.PasswordStorage toString: " + (toStringPassed ? "PASS" : "FAIL"));
        System.out.println("src.PasswordStorage isValidBST: " + (isValidBSTPassed ? "PASS" : "FAIL"));
        System.out.println("src.PasswordStorage lookup: " + (lookupPassed ? "PASS" : "FAIL"));
        System.out.println("src.PasswordStorage addPassword: " + (addPasswordPassed ? "PASS" : "FAIL"));
        System.out.println("src.PasswordStorage removePassword: " + (removePasswordPassed ? "PASS" : "FAIL"));

        // AND ANY OTHER ADDITIONAL TEST METHODS YOU MAY WANT TO WRITE!

        return compareToPassed && nodeStatusPassed && basicMethodsPassed && toStringPassed
                && isValidBSTPassed && lookupPassed && addPasswordPassed && removePasswordPassed;
    }

}
