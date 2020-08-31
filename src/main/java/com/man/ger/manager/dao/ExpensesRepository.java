package com.man.ger.manager.dao;

import com.man.ger.manager.entity.CategoryEnumStatus;
import com.man.ger.manager.entity.Expenses;
import com.man.ger.manager.entity.ExpensesEnumStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpensesRepository extends JpaRepository<Expenses, Long> {
  List<Expenses> findByIdAndDateBetween(Long id, LocalDate from, LocalDate to);

  @Query("SELECT exp FROM Expenses exp where exp.date=:date and exp.category.id=:categoryId")
  List<Expenses> getByDateAndCategory(@Param("date") LocalDate date, @Param("categoryId") Long categoryId);

  @Query("select exp from Expenses exp where exp.date=:date")
  List<Expenses> getByDate(@Param("date") LocalDate date);

  @Query(value = "SELECT SUM (exp.sum) FROM Expenses exp where exp.date=:date")
  BigDecimal getSummByDate(@Param("date") LocalDate date);

  @Query("UPDATE Expenses e set e.status=:status where e.id=:id")
  void deleteById(@Param("status") ExpensesEnumStatus status, @Param("id") Long id);

  List<Expenses> findByCategoryId(Long categoryId);

  @Query("SELECT exp from Expenses exp where exp.category.id=:categoryId ORDER BY  exp.id ASC  :limit ")
//@Query(value = "SELECT * FROM expenses e where e.category_id =:categoryId order by  id desc limit =:limit", nativeQuery = true)
  List<Expenses> getExpesesBySizeAndCategory(@Param("categoryId") Long categoryId, @Param("limit") int limit);

}
