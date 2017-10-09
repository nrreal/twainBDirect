public partial class Form1 : Form
{
    private DriveDetector driveDetector = null;
 
    public Form1()
    {
        InitializeComponent();
        driveDetector = new DriveDetector();
        driveDetector.DeviceArrived += new DriveDetectorEventHandler(
            OnDriveArrived);
        driveDetector.DeviceRemoved += new DriveDetectorEventHandler(
            OnDriveRemoved);
        driveDetector.QueryRemove += new DriveDetectorEventHandler(
   }            OnQueryRemove);
}
 
private void OnDriveArrived(object sender, DriveDetectorEventArgs e)
{
    e.HookQueryRemove = true;
}
 
 
private void OnDriveRemoved(object sender, DriveDetectorEventArgs e)
{
    // TODO: do clean up here, etc. Letter of the removed drive is in
    // e.Drive;
}
 
private void OnQueryRemove(object sender, DriveDetectorEventArgs e)
{
     if (MessageBox.Show("Allow remove?", "Query remove",
        MessageBoxButtons.YesNo, MessageBoxIcon.Question) ==
            DialogResult.Yes)
        e.Cancel = false;
        e.Cancel = true;
}
 
driveDetector = new DriveDetector(this);
 
protected override void WndProc(ref Message m)
{
    base.WndProc(ref m);        // call default p
 
    if (driveDetector != null)
    {
        driveDetector.WndProc(ref m);
    }
}