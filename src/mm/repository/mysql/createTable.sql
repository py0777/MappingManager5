
 
drop table META_TABLE_MAP;
drop table META_TABLE_MAP_CRR;
drop table META_TABLE_MAP_HST;

/*META_TABLE_MAP*/
create table META_TABLE_MAP(
  ID                       int not null primary key auto_increment               
, MAP_SORT                 int not null 
, MAP_ID                   VARCHAR(50) 
, T_SYSTEM_NAME            VARCHAR(50) 
, T_OWNER                  VARCHAR(50) 
, T_ENG_TABLE_NAME         VARCHAR(50) 
, T_KOR_TABLE_NAME         VARCHAR(50) 
, T_ENG_COLUMN_NAME        VARCHAR(50) 
, T_KOR_COLUMN_NAME        VARCHAR(50) 
, T_DATA_TYPE              VARCHAR(50) 
, T_LENGTH1                VARCHAR(50) 
, T_LENGTH2                VARCHAR(50) 
, T_PK                     VARCHAR(50) 
, A_SYSTEM_NAME            VARCHAR(50) 
, A_OWNER                  VARCHAR(50) 
, A_ENG_TABLE_NAME         VARCHAR(50) 
, A_KOR_TABLE_NAME         VARCHAR(50) 
, A_ENG_COLUMN_NAME        VARCHAR(50) 
, A_KOR_COLUMN_NAME        VARCHAR(50) 
, A_DATA_TYPE              VARCHAR(50) 
, A_LENGTH1                VARCHAR(10) 
, A_LENGTH2                VARCHAR(10) 
, A_PK                     VARCHAR(50) 
, MOVE_DEFAULT             VARCHAR(50) 
, MOVE_YN                  VARCHAR(50) 
, MOVE_RULE                VARCHAR(50) 
, MOVE_SQL                 VARCHAR(50) 
, ALT_EMP_NO               VARCHAR(50) 
, PREE_CDTN                VARCHAR(50) 
, ALT_DT                   VARCHAR(50) 
, JOB_OWNER                VARCHAR(50) 
, CLIENT_OWNER             VARCHAR(50) 
, MOVE_OWNER               VARCHAR(50) 
, MAP_STAT                 VARCHAR(10)  /*맵상태 1:반영완료 2:신청중 3:반영중 4:반려*/
, MAP_IUD_FLGCD            VARCHAR(10)  /*맵등록수정삭제 I:등록, U:수정: D: 삭제*/

);
ALTER TABLE META_TABLE_MAP ADD CONSTRAINT pk_META_TABLE_MAP PRIMARY KEY (ID);

GRANT SELECT, INSERT, UPDATE, DELETE ON META_TABLE_MAP TO py0777;

create table META_CODE_MAP(
  ID                     int not null primary key auto_increment               
, NO                     int not null 
, WORK_NAME              VARCHAR(50) 
, T_ENG_COLUMN_NAME      VARCHAR(50) 
, T_CODE_NAME            VARCHAR(50) 
, T_CODE_CD              VARCHAR(50) 
, T_CMT                  VARCHAR(50) 
, T_REMARK               VARCHAR(50) 
, A_KCODE_NAME           VARCHAR(50) 
, A_ECODE_NAME           VARCHAR(50) 
, A_CODE_CD              VARCHAR(50) 
, A_CMT                  VARCHAR(50) 
, A_REMARK               VARCHAR(50) 
, 업무담당자             VARCHAR(50) 
, 고객담당자             VARCHAR(50) 
, 이행담당자             VARCHAR(50) 
);
ALTER TABLE META_CODE_MAP ADD CONSTRAINT pk_META_CODE_MAP PRIMARY KEY (ID);

GRANT SELECT, INSERT, UPDATE, DELETE ON META_CODE_MAP TO ys2613;

create table META_TABLE_MAP_CRR(
CHNG_DT                    DATE not null 
,CHNG_SEQ                  INT        not null
,  ID                      INT                
, MAP_SORT                 INT  
, MAP_ID                   VARCHAR(50) 
, T_SYSTEM_NAME            VARCHAR(50) 
, T_OWNER                  VARCHAR(50) 
, T_ENG_TABLE_NAME         VARCHAR(50) 
, T_KOR_TABLE_NAME         VARCHAR(50) 
, T_ENG_COLUMN_NAME        VARCHAR(50) 
, T_KOR_COLUMN_NAME        VARCHAR(50) 
, T_DATA_TYPE              VARCHAR(50) 
, T_LENGTH1                VARCHAR(50) 
, T_LENGTH2                VARCHAR(50) 
, T_PK                     VARCHAR(50) 
, A_SYSTEM_NAME            VARCHAR(50) 
, A_OWNER                  VARCHAR(50) 
, A_ENG_TABLE_NAME         VARCHAR(50) 
, A_KOR_TABLE_NAME         VARCHAR(50) 
, A_ENG_COLUMN_NAME        VARCHAR(50) 
, A_KOR_COLUMN_NAME        VARCHAR(50) 
, A_DATA_TYPE              VARCHAR(50) 
, A_LENGTH1                VARCHAR(10) 
, A_LENGTH2                VARCHAR(10) 
, A_PK                     VARCHAR(50) 
, MOVE_DEFAULT             VARCHAR(50) 
, MOVE_YN                  VARCHAR(50) 
, MOVE_RULE                VARCHAR(50) 
, MOVE_SQL                 VARCHAR(50) 
, ALT_EMP_NO               VARCHAR(50) 
, PREE_CDTN                VARCHAR(50) 
, ALT_DT                   VARCHAR(50) 
, JOB_OWNER                VARCHAR(50) 
, CLIENT_OWNER             VARCHAR(50) 
, MOVE_OWNER               VARCHAR(50) 
, MAP_STAT                 VARCHAR(10)  /*맵상태 1:반영 2:신청중*/
, MAP_IUD_FLGCD            VARCHAR(10)  /*맵등록수정삭제 I:등록, U:수정: D: 삭제*/
, PRIMARY KEY (CHNG_DT,CHNG_SEQ)

);
ALTER TABLE META_TABLE_MAP_CRR ADD CONSTRAINT pk_META_TABLE_MAP_CRR PRIMARY KEY (CHNG_DT,CHNG_SEQ);
ALTER TABLE META_TABLE_MAP_CRR ADD  PRIMARY KEY (CHNG_DT,CHNG_SEQ);


create table META_TABLE_MAP_HST(
  CHNG_DT                    DATE not null 
, CHNG_SEQ                   INT  not null
, ID                         INT                
, BF_MAP_SORT                 INT 
, AF_MAP_SORT                 INT 
, BF_MAP_ID                   VARCHAR(50) 
, AF_MAP_ID                   VARCHAR(50) 
, BF_T_SYSTEM_NAME            VARCHAR(50) 
, AF_T_SYSTEM_NAME            VARCHAR(50) 
, BF_T_OWNER                  VARCHAR(50) 
, AF_T_OWNER                  VARCHAR(50) 
, BF_T_ENG_TABLE_NAME         VARCHAR(50)
, AF_T_ENG_TABLE_NAME         VARCHAR(50)
, BF_T_KOR_TABLE_NAME         VARCHAR(50)
, AF_T_KOR_TABLE_NAME         VARCHAR(50)
, BF_T_ENG_COLUMN_NAME        VARCHAR(50)
, AF_T_ENG_COLUMN_NAME        VARCHAR(50)
, BF_T_KOR_COLUMN_NAME        VARCHAR(50)
, AF_T_KOR_COLUMN_NAME        VARCHAR(50)
, BF_T_DATA_TYPE              VARCHAR(50) 
, AF_T_DATA_TYPE              VARCHAR(50) 
, BF_T_LENGTH1                VARCHAR(50) 
, AF_T_LENGTH1                VARCHAR(50) 
, BF_T_LENGTH2                VARCHAR(50) 
, AF_T_LENGTH2                VARCHAR(50) 
, BF_T_PK                     VARCHAR(50) 
, AF_T_PK                     VARCHAR(50) 
, BF_A_SYSTEM_NAME            VARCHAR(50) 
, AF_A_SYSTEM_NAME            VARCHAR(50) 
, BF_A_OWNER                  VARCHAR(50) 
, AF_A_OWNER                  VARCHAR(50) 
, BF_A_ENG_TABLE_NAME         VARCHAR(50) 
, AF_A_ENG_TABLE_NAME         VARCHAR(50) 
, BF_A_KOR_TABLE_NAME         VARCHAR(50) 
, AF_A_KOR_TABLE_NAME         VARCHAR(50) 
, BF_A_ENG_COLUMN_NAME        VARCHAR(50) 
, AF_A_ENG_COLUMN_NAME        VARCHAR(50) 
, BF_A_KOR_COLUMN_NAME        VARCHAR(50) 
, AF_A_KOR_COLUMN_NAME        VARCHAR(50) 
, BF_A_DATA_TYPE              VARCHAR(50) 
, AF_A_DATA_TYPE              VARCHAR(50) 
, BF_A_LENGTH1                VARCHAR(10) 
, AF_A_LENGTH1                VARCHAR(10) 
, BF_A_LENGTH2                VARCHAR(10) 
, AF_A_LENGTH2                VARCHAR(10)
, BF_A_PK                     VARCHAR(50) 
, AF_A_PK                     VARCHAR(50) 
, BF_MOVE_DEFAULT             VARCHAR(50) 
, AF_MOVE_DEFAULT             VARCHAR(50) 
, BF_MOVE_YN                  VARCHAR(50) 
, AF_MOVE_YN                  VARCHAR(50)
, BF_MOVE_RULE                VARCHAR(50) 
, AF_MOVE_RULE                VARCHAR(50) 
, BF_MOVE_SQL                 VARCHAR(50) 
, AF_MOVE_SQL                 VARCHAR(50)
, BF_ALT_EMP_NO               VARCHAR(50) 
, AF_ALT_EMP_NO               VARCHAR(50) 
, BF_PREE_CDTN                VARCHAR(50) 
, AF_PREE_CDTN                VARCHAR(50)
, BF_ALT_DT                   VARCHAR(50) 
, AF_ALT_DT                   VARCHAR(50) 
, BF_JOB_OWNER                VARCHAR(50) 
, AF_JOB_OWNER                VARCHAR(50)
, BF_CLIENT_OWNER             VARCHAR(50) 
, AF_CLIENT_OWNER             VARCHAR(50)
, BF_MOVE_OWNER               VARCHAR(50) 
, AF_MOVE_OWNER               VARCHAR(50)
, MAP_STAT                    VARCHAR(10)  /*맵상태 1:반영 2:신청중*/
, MAP_IUD_FLGCD            VARCHAR(10)  /*맵등록수정삭제 I:등록, U:수정: D: 삭제*/

, CONSTRAINT pk_META_TABLE_MAP_HST PRIMARY KEY (CHNG_DT,CHNG_SEQ)

);

