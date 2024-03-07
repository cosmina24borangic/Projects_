# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.
class A:
   def f(self, l,nr):
        l.append(nr)
class B:
   def g(self, l, nr):
         nr=nr-1
         l = l+[-2]
a = A()
b = B()
l = [1,2]
c = -1
a.f(l,6)
b.g(l,c)
print (l,c)