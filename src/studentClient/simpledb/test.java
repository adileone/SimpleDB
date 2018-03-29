package studentClient.simpledb;

import simpledb.tx.Transaction;
import simpledb.query.*;
import simpledb.server.SimpleDB;

/* This is a version of the StudentMajor program that
 * accesses the SimpleDB classes directly (instead of
 * connecting to it as a JDBC client).  You can run it
 * without having the server also run.
 * 
 * These kind of programs are useful for debugging
 * your changes to the SimpleDB source code.
 */

public class test {
	public static void main(String[] args) {
		try {
			// analogous to the driver
			SimpleDB.init("studentdb");

			// analogous to the connection
			Transaction tx = new Transaction();

			String s1 = "create table PROVATABLE(A varchar(10))";
			SimpleDB.planner().executeUpdate(s1, tx);
			System.out.println("Table PROVATABLE created.");


			for (int i=0; i<200000; i++) {

				String num = Double.toString(Math.random());
				String s = "insert into PROVATABLE(A) values ('"+num+"')";
				SimpleDB.planner().executeUpdate(s, tx);
			}

			System.out.println("inserted");

			String qry = "select A "
					+ "from PROVATABLE";	
			Plan p = SimpleDB.planner().createQueryPlan(qry, tx);

			// analogous to the result set
			Scan sc = p.open();

			while (sc.next()) {
				String sname = sc.getString("a"); //SimpleDB stores field names	
				System.out.println(sname + "\t");
			}
			sc.close();

			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
