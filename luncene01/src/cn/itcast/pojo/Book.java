package cn.itcast.pojo;
/** 
* @author lijin E-mail: lijin1@itcast.cn 
* @date   2017年9月17日 下午5:11:15 
* 类说明 
*/
public class Book {

	// 图书ID
	private Integer id;
	// 图书名称
	private String name;
	// 图书价格
	private Float price;
	// 图书图片
	private String pic;
	// 图书描述
	private String desc;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	

}
