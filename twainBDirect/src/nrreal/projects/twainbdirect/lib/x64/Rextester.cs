using System;
using System.Runtime.InteropServices;
using System.Security;
using System.Collections;
using System.Windows.Forms;

namespace Rextester
{
    public sealed class Program
    {

 
        #region Dll Imports
        [DllImport("Netapi32", CharSet = CharSet.Auto, SetLastError = true), SuppressUnmanagedCodeSecurityAttribute]
        public static extern int NetServerEnum(
            string ServerNane,
            int dwLevel,
            ref IntPtr pBuf,
            int dwPrefMaxLen,
            out int dwEntriesRead,
            out int dwTotalEntries,
            int dwServerType,
            string domain,
            out int dwResumeHandle
            );
        [DllImport("Netapi32", SetLastError = true), SuppressUnmanagedCodeSecurityAttribute]
        public static extern int NetApiBufferFree(
            IntPtr pBuf);
        [StructLayout(LayoutKind.Sequential)]
        public struct _SERVER_INFO_100
        {
            internal int sv100_platform_id;
            [MarshalAs(UnmanagedType.LPWStr)]
            internal string sv100_name;
        }
        #endregion
 
        #region Public Methods
        public ArrayList getNetworkComputers()
        {
            ArrayList networkComputers = new ArrayList();
            const int MAX_PREFERRED_LENGTH = -1;
            int SV_TYPE_WORKSTATION = 1;
            int SV_TYPE_SERVER = 2;
            IntPtr buffer = IntPtr.Zero;
            IntPtr tmpBuffer = IntPtr.Zero;
            int entriesRead = 0;
            int totalEntries = 0;
            int resHandle = 0;
            int sizeofINFO = Marshal.SizeOf(typeof(_SERVER_INFO_100));
            try
            {
 
                int ret = NetServerEnum(null, 100, ref buffer, MAX_PREFERRED_LENGTH, out entriesRead, out totalEntries, SV_TYPE_WORKSTATION | SV_TYPE_SERVER, null, out resHandle);
                if (ret == 0)
                {
                    for (int i = 0; i < totalEntries; i++)
                    {
                        tmpBuffer = new IntPtr((int)buffer + (i * sizeofINFO));
 
                        _SERVER_INFO_100 svrInfo = (_SERVER_INFO_100)
                            Marshal.PtrToStructure(tmpBuffer, typeof(_SERVER_INFO_100));
                        networkComputers.Add(svrInfo.sv100_name);
                    }
                }
            }
            catch (Exception ex)
            {
              Console.WriteLine(ex);
                return null;
            }
            finally
            {
                NetApiBufferFree(buffer);
            }
            return networkComputers;
        }
        
         public static void Main(string[] args)
        {
           Rextester.Program nb = new Rextester.Program();
foreach (string pc in nb.getNetworkComputers())
{
      System.Console.WriteLine(pc);
}
        }
        
        
        #endregion
    }
}
