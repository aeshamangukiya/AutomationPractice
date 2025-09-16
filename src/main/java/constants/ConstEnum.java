package constants;

public class ConstEnum {

    public enum OperatingSystem {
        WINDOWS, LINUX, MAC;
    }

    public enum Browser {
        CHROME, EDGE, FIREFOX;
    }



    public enum TestGroup {
        SANITY("sanity"),
        REGRESSION("regression"),
        MASTER("master"),
        DATA_DRIVEN("datadriven");

        private final String groupName;

        TestGroup(String groupName) {
            this.groupName = groupName;
        }

        public String getGroupName() {
            return groupName;
        }

		@Override
		public String toString() {
			return groupName;
		}

		// Compile-time constant Strings for annotation usage
		public static final String SANITY_CONST = "sanity";
		public static final String REGRESSION_CONST = "regression";
		public static final String MASTER_CONST = "master";
		public static final String DATA_DRIVEN_CONST = "datadriven";
	}

}

