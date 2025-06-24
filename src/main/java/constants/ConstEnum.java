package constants;

public class ConstEnum {

	public enum OperatingSystem {
		WINDOWS, LINUX, MAC;
	}

	public enum Browser {
		CHROME, EDGE, FIREFOX;
	}

	public enum TestGroups {
		SANITY_GROUP("sanity"), REGRESSION_GROUP("regression"), MASTER_GROUP("master"), DATA_DRIVEN_GROUP("datadriven");

		// Define constants as compile-time constants (public static final Strings)
		public static final String SANITY = "sanity";
		public static final String REGRESSION = "regression";
		public static final String MASTER = "master";
		public static final String DATA_DRIVEN = "datadriven";

		private final String groupName;

		TestGroups(String groupName) {
			this.groupName = groupName;
		}

		public String getGroupName() {
			return groupName;
		}
	}
}
