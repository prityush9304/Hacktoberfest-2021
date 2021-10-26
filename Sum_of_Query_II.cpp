#include <bits/stdc++.h>
using namespace std;
class Solution{
public:
    vector<int> querySum(int n, int arr[], int q, int queries[])
    {
        // code here
         vector<int> v;
        int i=0;
        
        int temp1=0,temp2=1;
        while(q--){
          int sum=0;
          int l=queries[temp1],r=queries[temp2];
          for(int i=l-1;i<r;i++){
              sum+=arr[i];
          }
          temp1+=2;
          temp2+=2;
          v.push_back(sum);
        }
        return v;
    }
};
int main(){
    int t;
    cin>>t;
    while(t--){
        int n, q;
        cin>>n;
        int arr[n];
        for(int i = 0;i < n;i++)
            cin>>arr[i];
        cin>>q;
        int queries[2*q];
        for(int i = 0;i < 2*q;i += 2)
            cin>>queries[i]>>queries[i+1];
        
        Solution ob;
        vector<int> ans = ob.querySum(n, arr, q, queries);
        for(int u: ans)
            cout<<u<<" ";
        cout<<endl;
    }
    return 0;
} 
