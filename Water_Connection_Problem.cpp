#include <bits/stdc++.h>
using namespace std;
class Solution
{
int mini;
    public:
     int dfs(int start[], int dia[], int x)
    {
        if(dia[x] < mini && dia[x] != 0) mini = dia[x];
        
        if(start[x] == 0) return x;
        
        return dfs(start, dia, start[x]);
    }
    vector<vector<int>> solve(int n,int p,vector<int> a,vector<int> b,vector<int> d)
    {
        // code here
        vector<vector<int>> answer;
        int start[n+1];
        int end[n+1];
        int dia[n+1];
        
        for(int i=0; i<=n; i++)
        {
            start[i]=0;
            end[i]=0;
            dia[i]=0;
        }
        
        for(int i=0; i<p; i++)
        {
            start[a[i]] = b[i];
            end[b[i]] = a[i];
            dia[a[i]] = d[i];
        }
        
        //preprocessing is done
    
        // for(int i=1; i<=n; i++)
        // cout << start[i] << " " << end[i] << " " << dia[i]
        
        for(int i=1; i<=n; i++)
        {
            if(start[i] != 0 && end[i] == 0)
            {
                mini = INT_MAX;
                int end = dfs(start, dia, i);
                answer.push_back({i, end, mini});
            }
        }
        
        return answer;
    }
};
int main()
{
	int t,n,p;
	cin>>t;
	while(t--)
    {
        cin>>n>>p;
        vector<int> a(p),b(p),d(p);
        for(int i=0;i<p;i++){
            cin>>a[i]>>b[i]>>d[i];
        }
        Solution obj;
        vector<vector<int>> answer = obj.solve(n,p,a,b,d);
        cout<<answer.size()<<endl;
        for(auto i:answer)
        {
            cout<<i[0]<<" "<<i[1]<<" "<<i[2]<<endl;
        }
        
    }
	return 0;
