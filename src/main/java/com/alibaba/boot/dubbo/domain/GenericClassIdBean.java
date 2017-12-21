package com.alibaba.boot.dubbo.domain;

import java.io.Serializable;

/**
 * 标示唯一一个class
 * 泛化调用
 * @author jjwu
 * @since 1.0.0
 */
public class GenericClassIdBean implements Serializable {
  private static final long serialVersionUID = 3744915458507135546L;

  private String interfaceName;
  private String group;
  private String version;

  public GenericClassIdBean(String interfaceName, String group, String version) {
    this.interfaceName = interfaceName;
    this.group = group;
    this.version = version;
  }

  public String getInterfaceName() {
    return interfaceName;
  }

  public void setInterfaceName(String interfaceName) {
    this.interfaceName = interfaceName;
  }

  public String getGroup() {
    return this.group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public String getVersion() {
    return this.version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof GenericClassIdBean)) {
      return false;
    }
    GenericClassIdBean classIdBean = (GenericClassIdBean) obj;
    if (this.interfaceName == null ? classIdBean.interfaceName != null : !this.interfaceName.equals(classIdBean.interfaceName)) {
      return false;
    }
    if (this.group == null ? classIdBean.group != null : !this.group.equals(classIdBean.group)) {
      return false;
    }
    return this.version == null ? classIdBean.version == null
        : this.version.equals(classIdBean.version);
  }

  @Override
  public int hashCode() {
    int hashCode = 17;
    hashCode = 31 * hashCode + (this.interfaceName == null ? 0 : this.interfaceName.hashCode());
    hashCode = 31 * hashCode + (this.group == null ? 0 : this.group.hashCode());
    hashCode = 31 * hashCode + (this.version == null ? 0 : this.version.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GenericClassIdBean [interfaceName=" + this.interfaceName + ", group=" + this.group + ", version="
        + this.version + "]";
  }
}
