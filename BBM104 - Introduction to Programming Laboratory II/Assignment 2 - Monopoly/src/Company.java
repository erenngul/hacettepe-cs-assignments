import java.util.HashMap;

public class Company extends Property {
	private static HashMap<Integer, Company> companyProperties = new HashMap<Integer, Company>(2);

	public Company(int propertyID, String propertyName, double propertyCost) {
		super(propertyID, propertyName, propertyCost);
	}
	
	// Takes company properties from property.json, creates company objects and makes pairs of id-object
	public static HashMap<Integer, Company> createCompanyProperties() {
		for (HashMap.Entry<Integer, String[]> entry : properties.getCompanies().entrySet()) {
			Integer key = entry.getKey();
			String[] value = entry.getValue();
			
			Company company = new Company(key, value[0], Integer.parseInt(value[1]));
			
			companyProperties.put(key, company);
		}
		
		return companyProperties;
	}

	public static HashMap<Integer, Company> getCompanyProperties() {
		return companyProperties;
	}
}