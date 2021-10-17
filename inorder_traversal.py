class Node:
    def __init__(self, key):
        self.left = None
        self.right = None
        self.val = key

def printInorder(root):
 
    if root:
        printInorder(root.left)
        print(root.val),
        printInorder(root.right)


# Driver code
root = Node(10)
root.left = Node(20)
root.right = Node(30)
root.left.left = Node(40)
root.left.right = Node(50)
 
print "\nInorder traversal of binary tree is"
printInorder(root)