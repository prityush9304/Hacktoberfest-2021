class Node:
    def __init__(self, key):
        self.left = None
        self.right = None
        self.val = key

def printPostorder(root):
 
    if root:
    	
        printInorder(root.left)
        printInorder(root.right)
        print(root.val),


# Driver code
root = Node(10)
root.left = Node(20)
root.right = Node(30)
root.left.left = Node(40)
root.left.right = Node(50)
 
print "\nPostorder traversal of binary tree is"
printPostorder(root)