package tn.esprit.models;

public enum Status {
    ACTIVE,
    INACTIVE;

    // Method to convert a string to the corresponding enum value
    public static Status fromString(String statusString) {
        for (Status status : Status.values()) {
            if (status.toString().equalsIgnoreCase(statusString)) {
                return status;
            }
        }
        // If no match is found, you may throw an exception, return a default value, or handle it as needed
        throw new IllegalArgumentException("No enum constant found for value: " + statusString);
    }
}
