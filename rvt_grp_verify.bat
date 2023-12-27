@echo off
@echo off
SET /p i=MVE ip address:
SET /p u=username:
SET /p p=password:
SET /p s=start test number:
SET /p e=end test number:

FOR /L %%A IN (%s%,1,%e%) DO (
    java rvt_grp_verify %i% %u% %p% %%A
)


cmd /k 