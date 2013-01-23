package cerebral.effect.dto;

public class AccountDto {
	private String clan_name;
	private String password;


	public AccountDto(){
		
	}
	
	public AccountDto(String clanname, String password){
		this.clan_name = clanname;
		this.password = password;
	}
	
	public void clearData(){
		clan_name = null;
		password = null;
	}

	public String getClan_name() {
		return clan_name;
	}

	public void setClan_name(String clan_name) {
		this.clan_name = clan_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toString(){
		if(clan_name != null && password != null){
			return clan_name + " : has password ";
		}else if(clan_name != null){
			return clan_name + " : has no password ";
		}else{
			return "This account is an empty object";
		}
	}
}
