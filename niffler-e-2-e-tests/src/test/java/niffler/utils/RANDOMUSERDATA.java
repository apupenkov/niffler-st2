package niffler.utils;

import com.github.javafaker.Faker;

public enum RANDOMUSERDATA {

    FAKE{

        private static final Faker faker = new Faker();
        private static final String name = faker.name().firstName();
        private static final String pass = faker.internet().password();

        public String getName() {
            return name;
        }

        public String getPass() {
            return pass;
        }
    };



    public String getName() {
        return "";
    }

    public String getPass() {
        return "";
    }
}
