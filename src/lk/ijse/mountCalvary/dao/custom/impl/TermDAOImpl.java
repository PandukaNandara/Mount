package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.TermDAO;
import lk.ijse.mountCalvary.entity.Term;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/21/2018
 * Time: 2:06 AM
 */
public class TermDAOImpl implements TermDAO {
    @Override
    public boolean save(Term entity) throws Exception {
        return CrudUtil.executeUpdate("insert into term values (?,?,?,?)",
                entity.getTERM_ID(),
                entity.getTermName(),
                entity.getYear(),
                entity.getTermNo()
        ) > 0;
    }

    @Override
    public boolean saveWithoutPKey(Term entity) throws Exception {
        return CrudUtil.executeUpdate("insert into term(term_name, year, term_no) values(?,?,?)",
                entity.getTermName(),
                entity.getYear(),
                entity.getTermNo()
        ) > 0;
    }

    @Override
    public boolean update(Term entity) throws Exception {
        return CrudUtil.executeUpdate("update term set term_name = ?, year = ?, term_no = ?  where TERM_ID = ?",
                entity.getTermName(),
                entity.getYear(),
                entity.getTermNo(),
                entity.getTERM_ID()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.executeUpdate("delete from term where TERM_ID = ?", id) > 0;
    }

    @Override
    public Term search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("select * from term where TERM_ID = ?", id);
        if (rst.next())
            return new Term(
                    rst.getInt("TERM_ID"),
                    rst.getString("term_name"),
                    rst.getInt("year"),
                    rst.getInt("term_no")
            );
        else return null;
    }

    @Override
    public ArrayList<Term> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("select * from term");
        ArrayList<Term> allTerms = new ArrayList<>();
        while (rst.next()) {
            allTerms.add(new Term(
                            rst.getInt("TERM_ID"),
                            rst.getString("term_name"),
                            rst.getInt("year"),
                            rst.getInt("term_no")
                    )
            );
        }
        return allTerms;
    }

    @Override
    public Integer lastIndex() {
        return null;
    }

    @Override
    public Integer getIncrementIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_name = 'term'\n" +
                "AND table_schema = DATABASE( ) ;");
        rst.next();
        return rst.getInt(1) - 1;
    }

    @Override
    public ArrayList<Integer> getDistinctYears() throws Exception {
        ResultSet rst = CrudUtil.executeQuery(
                "select distinct (Year)\n" +
                        "from term;");
        ArrayList<Integer> years = new ArrayList<>();
        while (rst.next()) {
            years.add(rst.getInt(1));
        }
        return years;
    }
}
