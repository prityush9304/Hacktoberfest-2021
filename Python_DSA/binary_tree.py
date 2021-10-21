from typing import List, Optional
from collections import deque
from drawtree import draw_bst, draw_level_order


class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

    def __str__(self) -> str:
        return str(self.val)+" -> "+str(self.left)+","+str(self.right)


def get_tree_from_list(nodes: List[int]) -> TreeNode:
    if len(nodes) == 0:
        return None

    nodes_ = list(nodes)

    root = TreeNode(nodes_[0])

    parents = []

    del(nodes_[0])

    children = [TreeNode(val) for val in nodes_]

    right = False

    temp_root = root

    while len(children) > 0:

        temp = children[0]

        del(children[0])

        if not temp:
            continue

        parents.append(temp)

        if not right:
            temp_root.left = temp
            right = True
        else:
            temp_root.right = temp
            right = False
            temp_root = parents[0]
            del[parents[0]]

    return root


def exists_in_tree(root: Optional[TreeNode], value: int) -> bool:
    if not root:
        return False

    if root.val == value:
        return True

    if exists_in_tree(root.left, value):
        return True

    return exists_in_tree(root.right, value)


def get_path_to_node(root: Optional[TreeNode], value: int) -> List[int]:

    if not root:
        return []

    def helper(root: Optional[TreeNode], value: int, path: List[int]) -> Optional[List]:

        if not root:
            return None

        path.append(root.val)

        if root.val == value:
            return path

        left_ = helper(root.left, value, path)

        if left_:
            return left_

        right_ = helper(root.right, value, path)

        if right_:
            return right_

        path.pop()

        return None

    path = helper(root, value, [])
    
    return path


def print_tree_preorder(root: Optional[TreeNode]) -> None:

    if root:
        print(str(root.val)+",", sep="", end=" ")

        if root.left:
            print_tree_preorder(root.left)

        if root.right:
            print_tree_preorder(root.right)


def display_tree_graphic(arr: List[int]):

    if arr:
        s = "{"
        for i in arr:
            if i:
                s += str(i)+","
            else:
                s += "#,"

        s = s[:-1] + "}"
        print('s is', s)
        draw_level_order(s)


def main():

    # draw_bst([1, 2, 3, 4, 5, 6, 7, 8, 9])

    tree_arr = [1, 2, 3, 4, None, 5, None, 6, 7, 8, None, 9]
    root = get_tree_from_list(tree_arr)
    print_tree_preorder(root)
    display_tree_graphic(tree_arr)
    print('Exists:', exists_in_tree(root, 6))
    print('Path to it:', get_path_to_node(root, 6))


if __name__ == "__main__":
    main()
