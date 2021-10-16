using System;
using System.Collections.Generic;
using System.Text;

namespace TwoStacksProblem
{
    public class DoubleStack<T>:IDoubleStack<T>
    {
        protected T[] stack;
        protected int size;
        protected int top1;
        protected int top2;
        
        public DoubleStack(int n)
        {
            this.size = n;
            stack = new T[size];
            this.top1 = -1;
            this.top2 = size;
        }

        public void clear1()
        {
            top1 = -1;
        }

        public void clear2()
        {
            top2 = size;
        }

        public int count1()
        {
            if (isEmpty1())
                return 0;
            else
                return top1 + 1;
        }

        public int count2()
        {
            if (isEmpty2())
                return 0;
            else
                return size - top2;
        }

        public bool isEmpty1()
        {
            return top1 == -1;
        }

        public bool isEmpty2()
        {
            return top2 == size;
        }

        public bool isFull1()
        {
            return (top1 == size || top1 == top2 - 1);
        }

        public bool isFull2()
        {
            return (top2 == -1 || top1 == top2 - 1);
        }

        public T Peek1()
        {
            if (top1 > -1)
                return stack[top1];
            else
                throw new Exception("Stack1 is Empty");
        }

        public T Peek2()
        {
            if (top2 < size)
                return stack[top2];
            else
                throw new Exception("Stack2 is Empty");
        }

        public T Pop1()
        {
            if (top1 > -1)
                return stack[top1--];
            else
                throw new Exception("Stack1 is Empty");
        }

        public T Pop2()
        {

            if (top2 < size)
                return stack[top2++];
            else
                throw new Exception("Stack2 is Empty");
        }

        public void print1()
        {
            if (!isEmpty1())
            {
                Console.WriteLine("Elements of Stack1 are--------------------");
                 for (int i = 0; i <= top1; i++)
                {
                    Console.WriteLine(stack[i]);
                }
            }
            else
                throw new Exception("Stack1 is EMPTY");
        }

        public void print2()
        {
            if (!isEmpty2())
            {
                Console.WriteLine("Elements of Stack2 are--------------------");
                 for (int i = size-1; i >= top2; i--)
                {
                    Console.WriteLine(stack[i]);
                }
            }
            else
                throw new Exception("Stack2 is EMPTY");
        }

        public void Push1(T item)
        {
            if (top1 < top2 - 1)
                stack[++top1] = item;
            else
                throw new Exception("Stack1 is Full");
        }

        public void Push2(T item)
        {
            if (top1 < top2 - 1)
                stack[--top2] = item;
            else
                throw new Exception("Stack2 is Full");
        }
    }

    public interface IDoubleStack<T>
    {
        void Push1(T item);
        void Push2(T item);
        T Pop1();
        T Pop2();
        T Peek1();
        T Peek2();
        bool isEmpty1();
        bool isEmpty2();
        bool isFull1();
        bool isFull2();
        int count1();
        int count2();
        void clear1();
        void clear2();
        void print1();
        void print2();

    }
    
    class Program
    {
        static void Main(string[] args)
        {
            IDoubleStack<int> s = new DoubleStack<int>(6);
           
            s.Push1(8);
            s.Push2(10);
            s.Push1(7);
            s.Push1(5);
            s.Push2(20);
            s.Push2(30);
            Console.WriteLine(s.count1());
            Console.WriteLine(s.count2());
            s.print1();
            s.print2();




        }
    }
}