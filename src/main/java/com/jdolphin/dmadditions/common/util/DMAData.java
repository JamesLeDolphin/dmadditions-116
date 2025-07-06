package com.jdolphin.dmadditions.common.util;

import com.jdolphin.dmadditions.common.tardis.BrokenTardisData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DMAData implements Serializable {
	private static final long serialVersionUID = -7476469726906116051L;

	public boolean hasCitadel = false;
	public List<BrokenTardisData> brokenTardisData = new ArrayList<>();
}
