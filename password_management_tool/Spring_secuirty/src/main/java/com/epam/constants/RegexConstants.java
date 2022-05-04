package com.epam.constants;

public class RegexConstants {
    private RegexConstants(){

    }
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[,~,!,@,#,$,%,^,&, *,(,),\\-,_,=,+,\\[,{,\\],},|,;,:,<,>,/,?])" + "(?=\\S+$).{"+Constraints.MIN_LENGTH_PASSWORD +","+Constraints.MAX_LENGTH_PASSWORD +"}$";
    public static final String URL_REGEX = "((http|https)://)(www.)?" + "[a-zA-Z0-9@:%._\\+~#?&//=]" + "{2,256}\\.[a-z]" + "{2,6}\\b([-a-zA-Z0-9@:%" + "._\\+~#?&//=]*)";
    public static final String ACCOUNT_NAME_REGEX = "^[A-Za-z]\\w{"+Constraints.MIN_LENGTH_NAME +","+Constraints.MAX_LENGTH_NAME +"}$";
    public static  final String USER_NAME_REGEX = "^[A-Za-z0-9_.-]\\w{"+Constraints.MIN_LENGTH_USERNAME +","+Constraints.MAX_LENGTH_USERNAME +"}$";
}





