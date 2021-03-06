package com.puppetlabs.gatling.node_simulations

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

import com.puppetlabs.gatling.runner.SimulationWithScenario
import org.joda.time.LocalDateTime
import org.joda.time.format.ISODateTimeFormat

class FOSS375CatalogZero extends SimulationWithScenario {

	val httpProtocol = http
		.baseURL("https://puppets-macbook-pro-3.local:8140")
		.acceptHeader("pson, b64_zlib_yaml, yaml, raw")
		.acceptEncodingHeader("identity")
		.contentTypeHeader("application/x-www-form-urlencoded")

	val headers_3 = Map("Accept" -> "pson, b64_zlib_yaml, yaml, dot, raw")

	val headers_108 = Map(
		"Accept" -> "pson, yaml",
		"Content-Type" -> "text/pson",
		"Connection" -> "close")

    val reportBody = ELFileBody("FOSS375CatalogZero_0108_request.txt")

    val uri1 = "https://puppets-macbook-pro-3.local:8140/production"

	val chain_0 = exec(http("node")
			.get("/production/node/centos65.localdomain?transaction_uuid=9c313c25-ef8c-4f53-b1dd-dfa119fe4197&fail_on_404=true"))
		.exec(http("filemeta pluginfacts")
			.get("/production/file_metadatas/pluginfacts?links=manage&ignore=.svn&ignore=CVS&ignore=.git&recurse=true&checksum_type=md5"))
		.exec(http("filemeta plugins")
			.get("/production/file_metadatas/plugins?links=manage&ignore=.svn&ignore=CVS&ignore=.git&recurse=true&checksum_type=md5"))
		.pause(327 milliseconds)
		.exec(http("catalog")
			.post("/production/catalog/centos65.localdomain")
			.headers(headers_3)
			.formParam("facts_format", "pson")
			.formParam("transaction_uuid", "9c313c25-ef8c-4f53-b1dd-dfa119fe4197")
			.formParam("facts", "%7B%22expiration%22%3A%222015-04-14T03%3A58%3A39.789080000-07%3A00%22%2C%22values%22%3A%7B%22operatingsystemmajrelease%22%3A%226%22%2C%22ipaddress_eth0%22%3A%22172.16.105.128%22%2C%22blockdevices%22%3A%22sda%2Csr0%22%2C%22blockdevice_sr0_vendor%22%3A%22NECVMWar%22%2C%22bios_version%22%3A%226.00%22%2C%22swapfree%22%3A%221.94+GB%22%2C%22boardmanufacturer%22%3A%22Intel+Corporation%22%2C%22processor0%22%3A%22Intel%28R%29+Core%28TM%29+i7-3740QM+CPU+%40+2.70GHz%22%2C%22kernelrelease%22%3A%222.6.32-431.el6.x86_64%22%2C%22type%22%3A%22Other%22%2C%22domain%22%3A%22localdomain%22%2C%22mtu_eth0%22%3A%221500%22%2C%22sshdsakey%22%3A%22AAAAB3NzaC1kc3MAAACBAOZ%2FzZDPHeu8P2XS4Jr77lSC9lX4x9ut7ByFNXZsp6fHq55YfyF0Q0mmhH2diKsyIaVLGwqpQmZs%2B%2FRW8rgbRsO3vSdqlHg1iFLWmaEpeNWA9oKXVj1G2OjY0%2BHIwFArR%2Ft4jxgG0Vjn2P1GdbbWjTnTuQqqsHkbGp%2BGSjKi8q5zAAAAFQDP2WtNziegjLQzIKffkVwEEO9BBQAAAIAli3RP1X588FvbwM%2FNYe3I5L%2FoRrYTlF1XLRFV0K4t5HSGVMir%2B2zTY12ukaz0zBsdjkIcmpJjGK3m1FiRfbuUjso7bsaRgqOY0sBvni1OXzEtDOSxWY4XuiYOKtgj%2FIZdfsZ0%2FZ0mZt6BjTZN1SdxIHwFwKXUdT%2BYS0UTlt5EygAAAIB1yvPv8k1UkavkKXqpgvNcmxW%2Fi%2F41KzwtLo7ArktqQeRwkifR%2F0%2BsbmL%2FvgCbKxQ2cVpeoHbamyJ%2FPkS0KOMDpkiV1G9RSng7DnFF9xBz7h9xaVvrFrR9ddP%2Ff8k3eNg8WUNjn%2Bno63UK3Ka3nAujEYzoBxnFLeXejoPgkWfBuQ%3D%3D%22%2C%22uptime_seconds%22%3A%224256%22%2C%22bios_vendor%22%3A%22Phoenix+Technologies+LTD%22%2C%22physicalprocessorcount%22%3A%221%22%2C%22blockdevice_sda_model%22%3A%22VMware+Virtual+S%22%2C%22blockdevice_sr0_model%22%3A%22VMware+IDE+CDR10%22%2C%22hardwaremodel%22%3A%22x86_64%22%2C%22sshrsakey%22%3A%22AAAAB3NzaC1yc2EAAAABIwAAAQEAo77EHHpZ5bd6nsXYJYxmakRKv%2BfFUr4Ld6eakhFGbkw2KwO7XmYbOS6qqW2F%2F2rqrcBKmtDbnKIysqcp%2Fv01qvmfAX9S3Hi0AgSYAzfuZ6lEkWh%2FXeKuF0IzeolT8WTn8iTnR%2BCPMy46twx5C%2Bioux%2FrNrsReKN4EfI1wC3GEG01Bt4WtbRCzjVFvNTz%2BMiRoi4iMGoY4WdRLR2x3UU9kTAKpEvm23Lb%2F5p9g0N9v3LtevlVIwTSdyIHXbLjCNfuT4HuRUxTQdLPMa9kFwOtWhhbFY6VH5wbnvd0GOK%2FcrBUaeKf9DnQQu41b5B3rdHIL%2BIoC6AybsUl6ppIa%2FlNWw%3D%3D%22%2C%22netmask%22%3A%22255.255.255.0%22%2C%22architecture%22%3A%22x86_64%22%2C%22swapsize%22%3A%221.94+GB%22%2C%22boardserialnumber%22%3A%22None%22%2C%22path%22%3A%22%2Fusr%2Flocal%2Fsbin%3A%2Fusr%2Flocal%2Fbin%3A%2Fsbin%3A%2Fbin%3A%2Fusr%2Fsbin%3A%2Fusr%2Fbin%3A%2Froot%2Fbin%22%2C%22os%22%3A%22familyRedHatnameCentOSreleasefull6.5minor5major6%22%2C%22productname%22%3A%22VMware+Virtual+Platform%22%2C%22sshfp_rsa%22%3A%22SSHFP+1+1+014dbe1a811f483eadb0fdb2283b1ee22ade63d7%5CnSSHFP+1+2+12244974ff66aa9b6894883f3bffc47d670a694f9247c5090e0e408e7280b2c0%22%2C%22ps%22%3A%22ps+-ef%22%2C%22ipaddress%22%3A%22172.16.105.128%22%2C%22blockdevice_sr0_size%22%3A%221073741312%22%2C%22selinux%22%3A%22false%22%2C%22uniqueid%22%3A%2210ac8069%22%2C%22blockdevice_sda_vendor%22%3A%22VMware%2C%22%2C%22id%22%3A%22root%22%2C%22memoryfree_mb%22%3A%22870.29%22%2C%22processors%22%3A%22modelsIntel%28R%29+Core%28TM%29+i7-3740QM+CPU+%40+2.70GHzcount1physicalcount1%22%2C%22uptime%22%3A%221%3A10+hours%22%2C%22memoryfree%22%3A%22870.29+MB%22%2C%22uptime_days%22%3A%220%22%2C%22clientcert%22%3A%22centos65.localdomain%22%2C%22rubyplatform%22%3A%22x86_64-linux%22%2C%22operatingsystem%22%3A%22CentOS%22%2C%22macaddress_eth0%22%3A%2200%3A0C%3A29%3ACB%3AEC%3AED%22%2C%22virtual%22%3A%22vmware%22%2C%22augeasversion%22%3A%221.0.0%22%2C%22manufacturer%22%3A%22VMware%2C+Inc.%22%2C%22uuid%22%3A%22564D939C-9536-85C9-E8FA-53272CCBECED%22%2C%22osfamily%22%3A%22RedHat%22%2C%22memorysize%22%3A%22988.62+MB%22%2C%22clientnoop%22%3A%22false%22%2C%22ipaddress_lo%22%3A%22127.0.0.1%22%2C%22operatingsystemrelease%22%3A%226.5%22%2C%22memorysize_mb%22%3A%22988.62%22%2C%22boardproductname%22%3A%22440BX+Desktop+Reference+Platform%22%2C%22network_eth0%22%3A%22172.16.105.0%22%2C%22system_uptime%22%3A%22seconds4256days0hours1uptime1%3A10+hours%22%2C%22blockdevice_sda_size%22%3A%2221474836480%22%2C%22bios_release_date%22%3A%2207%2F31%2F2013%22%2C%22filesystems%22%3A%22ext4%2Ciso9660%22%2C%22swapsize_mb%22%3A%221983.99%22%2C%22processorcount%22%3A%221%22%2C%22facterversion%22%3A%222.4.3%22%2C%22mtu_lo%22%3A%2216436%22%2C%22hardwareisa%22%3A%22x86_64%22%2C%22rubysitedir%22%3A%22%2Fusr%2Flib%2Fruby%2Fsite_ruby%2F1.8%22%2C%22timezone%22%3A%22PDT%22%2C%22partitions%22%3A%22sda1mount%2Fbootfilesystemext4uuid984f9910-a016-4944-9d07-0aa193dd8aaesize1024000sda2filesystemLVM2_membersize40916992%22%2C%22fqdn%22%3A%22centos65.localdomain%22%2C%22swapfree_mb%22%3A%221983.99%22%2C%22netmask_eth0%22%3A%22255.255.255.0%22%2C%22puppetversion%22%3A%223.7.5%22%2C%22kernelmajversion%22%3A%222.6%22%2C%22hostname%22%3A%22centos65%22%2C%22is_virtual%22%3A%22true%22%2C%22rubyversion%22%3A%221.8.7%22%2C%22kernel%22%3A%22Linux%22%2C%22sshfp_dsa%22%3A%22SSHFP+2+1+4823e20a40a1a2dda9d1014bcfd578a2958dbf96%5CnSSHFP+2+2+91a45d52997f4885e81971c0b5ad499c5dad57bab187d6c99138dd8a813be8d4%22%2C%22kernelversion%22%3A%222.6.32%22%2C%22interfaces%22%3A%22eth0%2Clo%22%2C%22network_lo%22%3A%22127.0.0.0%22%2C%22clientversion%22%3A%223.7.5%22%2C%22gid%22%3A%22root%22%2C%22macaddress%22%3A%2200%3A0C%3A29%3ACB%3AEC%3AED%22%2C%22uptime_hours%22%3A%221%22%2C%22netmask_lo%22%3A%22255.0.0.0%22%2C%22serialnumber%22%3A%22VMware-56+4d+93+9c+95+36+85+c9-e8+fa+53+27+2c+cb+ec+ed%22%7D%2C%22name%22%3A%22centos65.localdomain%22%2C%22timestamp%22%3A%22Tue+Apr+14+03%3A28%3A39+-0700+2015%22%7D")
			.formParam("fail_on_404", "true"))
		.pause(2)
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero7/catalog_zero7_impl82.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero7/catalog_zero7_impl87.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero15/catalog_zero15_impl72.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero15/catalog_zero15_impl73.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero28/catalog_zero28_impl51.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero8/catalog_zero8_impl13.txt?links=manage&source_permissions=use"))
		.pause(121 milliseconds)
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero7/catalog_zero7_impl85.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero19/catalog_zero19_impl31.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero8/catalog_zero8_impl11.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero18/catalog_zero18_impl14.txt?links=manage&source_permissions=use"))
		.pause(116 milliseconds)
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero16/catalog_zero16_impl83.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero14/catalog_zero14_impl44.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero18/catalog_zero18_impl23.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero18/catalog_zero18_impl24.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero19/catalog_zero19_impl86.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero21/catalog_zero21_impl87.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero29/catalog_zero29_impl86.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero21/catalog_zero21_impl82.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero19/catalog_zero19_impl12.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero28/catalog_zero28_impl84.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero10/catalog_zero10_impl85.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero23/catalog_zero23_impl62.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero30/catalog_zero30_impl34.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero24/catalog_zero24_impl52.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero16/catalog_zero16_impl85.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero19/catalog_zero19_impl53.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero9/catalog_zero9_impl84.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero19/catalog_zero19_impl54.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero22/catalog_zero22_impl34.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero17/catalog_zero17_impl87.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero25/catalog_zero25_impl13.txt?links=manage&source_permissions=use"))
		.pause(108 milliseconds)
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero10/catalog_zero10_impl83.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero12/catalog_zero12_impl87.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero12/catalog_zero12_impl86.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero25/catalog_zero25_impl24.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero20/catalog_zero20_impl24.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero3/catalog_zero3_impl74.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero14/catalog_zero14_impl72.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero28/catalog_zero28_impl13.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero25/catalog_zero25_impl51.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero13/catalog_zero13_impl51.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero26/catalog_zero26_impl64.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero29/catalog_zero29_impl71.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero9/catalog_zero9_impl63.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero21/catalog_zero21_impl86.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero11/catalog_zero11_impl11.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero20/catalog_zero20_impl42.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero23/catalog_zero23_impl21.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero15/catalog_zero15_impl62.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero2/catalog_zero2_impl51.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero10/catalog_zero10_impl22.txt?links=manage&source_permissions=use"))
		.pause(109 milliseconds)
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero11/catalog_zero11_impl84.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero27/catalog_zero27_impl41.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero3/catalog_zero3_impl23.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero11/catalog_zero11_impl21.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero11/catalog_zero11_impl62.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero13/catalog_zero13_impl84.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero13/catalog_zero13_impl86.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero13/catalog_zero13_impl83.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero8/catalog_zero8_impl81.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero22/catalog_zero22_impl52.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero21/catalog_zero21_impl41.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero28/catalog_zero28_impl33.txt?links=manage&source_permissions=use"))
		.pause(132 milliseconds)
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero26/catalog_zero26_impl86.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero20/catalog_zero20_impl73.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero20/catalog_zero20_impl71.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero1/catalog_zero1_impl71.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero18/catalog_zero18_impl84.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero13/catalog_zero13_impl74.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero30/catalog_zero30_impl85.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero27/catalog_zero27_impl33.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero28/catalog_zero28_impl42.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero8/catalog_zero8_impl33.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero5/catalog_zero5_impl52.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero9/catalog_zero9_impl72.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero26/catalog_zero26_impl54.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero5/catalog_zero5_impl43.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero19/catalog_zero19_impl83.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero10/catalog_zero10_impl13.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero5/catalog_zero5_impl22.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero6/catalog_zero6_impl54.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero27/catalog_zero27_impl63.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero12/catalog_zero12_impl33.txt?links=manage&source_permissions=use"))
		.pause(103 milliseconds)
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero17/catalog_zero17_impl13.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero21/catalog_zero21_impl32.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero1/catalog_zero1_impl24.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero16/catalog_zero16_impl33.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero3/catalog_zero3_impl32.txt?links=manage&source_permissions=use"))

val chain_1 = exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero11/catalog_zero11_impl24.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero13/catalog_zero13_impl54.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero6/catalog_zero6_impl32.txt?links=manage&source_permissions=use"))
		.pause(119 milliseconds)
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero25/catalog_zero25_impl22.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero28/catalog_zero28_impl72.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero12/catalog_zero12_impl82.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero14/catalog_zero14_impl61.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero5/catalog_zero5_impl42.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero10/catalog_zero10_impl41.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero24/catalog_zero24_impl83.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero1/catalog_zero1_impl83.txt?links=manage&source_permissions=use"))
		.pause(164 milliseconds)
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero7/catalog_zero7_impl32.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero7/catalog_zero7_impl34.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero4/catalog_zero4_impl13.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero10/catalog_zero10_impl71.txt?links=manage&source_permissions=use"))
		.exec(http("filemeta")
			.get("/production/file_metadata/modules/catalog_zero18/catalog_zero18_impl32.txt?links=manage&source_permissions=use"))
		.pause(805 milliseconds)
                .exec((session:Session) => {
                   session.set("reportTimestamp", LocalDateTime.now.toString(ISODateTimeFormat.dateTime()))
                })
		.exec(http("report")
			.put("/production/report/centos65.localdomain")
			.headers(headers_108)
			.body(reportBody))
					
	val scn = scenario("FOSS375CatalogZero").exec(
		chain_0, chain_1)

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
