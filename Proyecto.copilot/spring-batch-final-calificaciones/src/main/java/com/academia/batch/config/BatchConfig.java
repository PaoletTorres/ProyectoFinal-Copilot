package com.academia.batch.config;

import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte;
import com.academia.batch.processor.EstudianteProcessor;
import com.academia.batch.processor.ReporteEstudianteProcessor;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final DataSource dataSource;
	private final MongoTemplate mongoTemplate;

	public BatchConfig(
			JobRepository jobRepository,
			PlatformTransactionManager transactionManager,
			DataSource dataSource,
			MongoTemplate mongoTemplate) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
		this.dataSource = dataSource;
		this.mongoTemplate = mongoTemplate;
	}

    @Bean
    public FlatFileItemReader<Estudiante> estudianteReader() {
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames("nombre", "grupo", "nota1", "nota2", "nota3");

		BeanWrapperFieldSetMapper<Estudiante> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Estudiante.class);

		DefaultLineMapper<Estudiante> lineMapper = new DefaultLineMapper<>();
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);

		return new FlatFileItemReaderBuilder<Estudiante>()
				.name("estudianteReader")
				.resource(new ClassPathResource("estudiantes.csv"))
				.linesToSkip(1)
				.lineMapper(lineMapper)
				.build();
    }

    @Bean
	public JdbcBatchItemWriter<Estudiante> estudianteWriter() {
		return new JdbcBatchItemWriterBuilder<Estudiante>()
				.dataSource(dataSource)
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO estudiantes_procesados (nombre, grupo, nota1, nota2, nota3, promedio) "
						+ " VALUES (:nombre, :grupo, :nota1, :nota2, :nota3, :promedio)")
				.build();
    }

    @Bean
	public JdbcCursorItemReader<Estudiante> estudianteProcesadoReader() {
		return new JdbcCursorItemReaderBuilder<Estudiante>()
				.name("estudianteProcesadoReader")
				.dataSource(dataSource)
				.sql("SELECT nombre, grupo, promedio FROM estudiantes_procesados")
				.rowMapper(new BeanPropertyRowMapper<>(Estudiante.class))
				.build();
    }

    @Bean
	public MongoItemWriter<EstudianteReporte> reporteWriter() {
		return new MongoItemWriterBuilder<EstudianteReporte>()
				.template(mongoTemplate)
				.collection("reportes_estudiantes")
				.build();
    }

    @Bean
    public Step paso1(
			FlatFileItemReader<Estudiante> estudianteReader,
			EstudianteProcessor estudianteProcessor,
			JdbcBatchItemWriter<Estudiante> estudianteWriter) {
		return new StepBuilder("paso1", jobRepository)
				.<Estudiante, Estudiante>chunk(3, transactionManager)
				.reader(estudianteReader)
				.processor(estudianteProcessor)
				.writer(estudianteWriter)
				.build();
    }

    @Bean
    public Step paso2(
			JdbcCursorItemReader<Estudiante> estudianteProcesadoReader,
			ReporteEstudianteProcessor reporteEstudianteProcessor,
			MongoItemWriter<EstudianteReporte> reporteWriter) {
		return new StepBuilder("paso2", jobRepository)
				.<Estudiante, EstudianteReporte>chunk(3, transactionManager)
				.reader(estudianteProcesadoReader)
				.processor(reporteEstudianteProcessor)
				.writer(reporteWriter)
				.build();
    }

    @Bean
	public Job procesarCalificacionesJob(Step paso1, Step paso2) {
		return new JobBuilder("procesarCalificacionesJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(paso1)
				.next(paso2)
				.build();
    }
}
