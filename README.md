# Bpmn-CWP

This is a customized / extended version of the open bpmn server. The extensions are:

1) a field for the source provenance of a data item 

2) a field for the destination provenance of a data item

3) a field for Promela code that describes how task inputs get mapped / transformed into task outputs. 

Usage
-----
4) These 3 new fields are in their own UI sub-panel for object properties. To get to this panel, double-left-click on the task in question. The object properties UI appears in the center bottom, with 2 sub-panels on the left side. The top is 'General'; the bottom is 'Verify_CWP'. Click on the top to set the task name and documentation. Click on the bottom to set any of the above 3 fields.

Build 
-----
The top-level Maven project manages the builds of the 2 sub-projects - the Java BPMN metamodel and the Java server-side component (the client component is what you see in VS Code). Any Java IDE that supports Maven should be able to build it. The output jar file goes in the 'target' subdirectory of the server sub-project. The generated jar file name currently looks like this: open-bpmn.server-1.2.7-SNAPSHOT-glsp.jar, where 1.2.7 is a build version.  This file name *must* be changed to imixs-open-bpmn.server-1.2.2-SNAPSHOT-glsp.jar in order for it to work as a server-side update to an installed VS Code open bpmn extension.  

Deploy
------
The above renamed jar file replaces the existing one of the same name in the 'server' subdirectory of the open-bpmn vscode extension directory. On Windows, this extension directory is in 
C:\Users\{your_user_id}\.vscode\extensions\open-bpmn.open-bpmn-vscode-extension-{version_#}\server .

Make a copy of the existing jar file in case of a problem, then delete it and copy the new renamed jar file in it's place. VS Code should start the server as before, and the graphical view of the BPMN network should appear as normal.



