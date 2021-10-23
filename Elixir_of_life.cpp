#include <bits/stdc++.h>
using namespace std;


 // } Driver Code Ends
//User function Template for C++

class Solution
{
    public:
    int maxFrequency(string s)
    {
     int n = s.size(), mn = n + 1, ans = 1, z[n] = {};
    for (int i = 1, l = 0, r = 0; i < n; i++) {
        if (i <= r)z[i] = min(r - i + 1, z[i - l]);
        while (i + z[i] < n && s[z[i]] == s[i + z[i]])z[i]++;
        if (i + z[i] - 1 > r)l = i, r = i + z[i] - 1;
        if (z[i] && z[i] + i == n)mn = min(mn, z[i]);
    }
    for (int i = 1; i < n; i++)if (z[i] >= mn)ans++;
    return ans;	// code here
    }
};
int main() 
{
   	ios_base::sync_with_stdio(0);
    cin.tie(NULL);
    cout.tie(NULL);
   	int t;
   	cin >> t;
   	while(t--)
   	{
   		string str;
   		cin >> str;
   		Solution obj;
   		cout << obj.maxFrequency(str) << "\n";
   	}
    return 0;
} 
