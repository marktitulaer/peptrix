package pharmaceuticals.nl.peptrix.allocation;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.service.GroupService;
import pharmaceuticals.nl.peptrix.serviceimpl.GroupServiceImpl;

public class InsertGroup {

	Controller cc;

	GroupService groupService;

	public InsertGroup(Controller cc) {
		this.cc = cc;
	}

	public String insertgroup(String strGroup_code, String strGroup_name, String strexperimentid) {

		groupService = new GroupServiceImpl(cc);

		String groupid = "";

		Object[][] odatagroup = groupService.selectgroup(strGroup_code, strexperimentid);

		if (odatagroup.length > 0) {
			groupid = odatagroup[0][0].toString().trim();
		} else {
			int updategroup = groupService.insertgroup(strGroup_code, strGroup_name);
			if (updategroup > 0) {
				groupid = groupService.getmaxgroupid(strGroup_code);
			}
		}
		return groupid;
	}
}
